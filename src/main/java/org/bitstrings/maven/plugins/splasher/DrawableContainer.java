/**
 *  Copyright (c) 2013 bitstrings.org - Pino Silvaggio
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.bitstrings.maven.plugins.splasher;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;


public abstract class DrawableContainer
    extends Drawable
{
    // - parameters --[

    /**
     * The drawables for this container.
     */
    private List<Drawable> draw;

    // ]--

    public List<Drawable> getDraw()
    {
        return ObjectUtils.defaultIfNull( draw, EMPTY_LIST );
    }

    public void setDraw( List<Drawable> draw )
    {
        this.draw = draw;
    }
}
