/*
 * Copyright 2006 Patrick Wright
 * Copyright 2007 Wisconsin Court System
 * Copyright 2020 White Magic Software, Ltd.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package com.keenwrite.preview;

import com.keenwrite.adapters.ReplacedElementAdapter;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;

import java.util.HashSet;
import java.util.Set;

public class ChainedReplacedElementFactory extends ReplacedElementAdapter {
  private final Set<ReplacedElementFactory> mFactoryList = new HashSet<>();

  @Override
  public ReplacedElement createReplacedElement(
      final LayoutContext c,
      final BlockBox box,
      final UserAgentCallback uac,
      final int cssWidth,
      final int cssHeight ) {
    for( final var f : mFactoryList ) {
      final var r = f.createReplacedElement(
          c, box, uac, cssWidth, cssHeight );

      if( r != null ) {
        return r;
      }
    }

    return null;
  }

  @Override
  public void reset() {
    for( final var factory : mFactoryList ) {
      factory.reset();
    }
  }

  @Override
  public void remove( final Element element ) {
    for( final var factory : mFactoryList ) {
      factory.remove( element );
    }
  }

  public void addFactory( final ReplacedElementFactory factory ) {
    mFactoryList.add( factory );
  }
}
