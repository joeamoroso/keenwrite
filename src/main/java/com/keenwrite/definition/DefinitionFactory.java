/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.keenwrite.definition;

import com.keenwrite.AbstractFileFactory;
import com.keenwrite.FileType;
import com.keenwrite.definition.yaml.YamlDefinitionSource;
import com.keenwrite.util.ProtocolScheme;

import java.nio.file.Path;

import static com.keenwrite.Constants.GLOB_PREFIX_DEFINITION;
import static com.keenwrite.FileType.YAML;
import static com.keenwrite.util.ProtocolResolver.getProtocol;
import static java.lang.String.format;

/**
 * Responsible for creating objects that can read and write definition data
 * sources. The data source could be YAML, TOML, JSON, flat files, or from a
 * database.
 */
public class DefinitionFactory extends AbstractFileFactory {

  /**
   * TODO: Use an error message key from messages properties file.
   */
  private static final String MSG_UNKNOWN_FILE_TYPE =
      "Unknown type '%s' for file '%s'.";

  /**
   * Default (empty) constructor.
   */
  public DefinitionFactory() {
  }

  /**
   * Creates a definition source capable of reading definitions from the given
   * path.
   *
   * @param path Path to a resource containing definitions.
   * @return The definition source appropriate for the given path.
   */
  public DefinitionSource createDefinitionSource( final Path path ) {
    assert path != null;

    final var protocol = getProtocol( path.toString() );
    DefinitionSource result = null;

    if( protocol.isFile() ) {
      final FileType filetype = lookup( path, GLOB_PREFIX_DEFINITION );
      result = createFileDefinitionSource( filetype, path );
    }
    else {
      unknownFileType( protocol, path.toString() );
    }

    return result;
  }

  /**
   * Creates a definition source based on the file type.
   *
   * @param filetype Property key name suffix from settings.properties file.
   * @param path     Path to the file that corresponds to the extension.
   * @return A DefinitionSource capable of parsing the data stored at the path.
   */
  private DefinitionSource createFileDefinitionSource(
      final FileType filetype, final Path path ) {
    assert filetype != null;
    assert path != null;

    if( filetype == YAML ) {
      return new YamlDefinitionSource( path );
    }

    throw new IllegalArgumentException( filetype.toString() );
  }

  /**
   * Throws IllegalArgumentException because the given path could not be
   * recognized. This exists because
   *
   * @param type The detected path type (protocol, file extension, etc.).
   * @param path The path to a source of definitions.
   */
  private void unknownFileType(
      final ProtocolScheme type, final String path ) {
    final String msg = format( MSG_UNKNOWN_FILE_TYPE, type, path );
    throw new IllegalArgumentException( msg );
  }
}
