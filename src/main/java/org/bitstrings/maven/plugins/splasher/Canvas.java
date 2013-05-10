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


public class Canvas
    extends PositionalLayout
{
    // - parameters --[

    private String backgroundImageName;

    private String backgroundColor;

    // ]--

    public String getBackgroundImageName()
    {
        return backgroundImageName;
    }

    public void setBackgroundImageName( String backgroundImageName )
    {
        this.backgroundImageName = backgroundImageName;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor( String backgroundColor )
    {
        this.backgroundColor = backgroundColor;
    }
}
