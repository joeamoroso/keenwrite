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
package com.keenwrite.service;

import com.dlsc.preferencesfx.PreferencesFx;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Responsible for persisting options that are safe to load before the UI
 * is shown. This can include items like window dimensions, last file
 * opened, split pane locations, and more. This cannot be used to persist
 * options that are user-controlled (i.e., all options available through
 * {@link PreferencesFx}).
 */
public interface Options extends Service {

  /**
   * Returns the {@link Preferences} that persist settings that cannot
   * be configured via the user interface.
   *
   * @return A valid {@link Preferences} instance, never {@code null}.
   */
  Preferences getState();

  /**
   * Stores the key and value into the user preferences to be loaded the next
   * time the application is launched.
   *
   * @param key   Name of the key to persist along with its value.
   * @param value Value to associate with the key.
   * @throws BackingStoreException Could not persist the change.
   */
  void put( String key, String value ) throws BackingStoreException;

  /**
   * Retrieves the value for a key in the user preferences.
   *
   * @param key          Retrieve the value of this key.
   * @param defaultValue The value to return in the event that the given key has
   *                     no associated value.
   * @return The value associated with the key.
   */
  String get( String key, String defaultValue );

  /**
   * Retrieves the value for a key in the user preferences. This will return
   * the empty string if the value cannot be found.
   *
   * @param key The key to find in the preferences.
   * @return A non-null, possibly empty value for the key.
   */
  String get( String key );
}
