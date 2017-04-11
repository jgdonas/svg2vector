/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.svg2vector.converters;

import java.awt.Color;

import org.freehep.graphicsio.emf.EMFGraphics2D;

/**
 * Properties for the EMF target.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v2.0.0-SNAPSHOT build 170411 (11-Apr-17) for Java 1.8
 * @since      v1.0.0
 */
public class TargetProperties_Emf extends TargetProperties {

	/**
	 * Constructor.
	 * Sets transparency to true, background to false and background color to white.
	 */
	public TargetProperties_Emf(){
		super();
		this.setPropertyTransparent(true);
		this.setPropertyBackground(false);
		this.setPropertyBackgroundColor(Color.WHITE);
	}

	@Override
	public void setPropertyBackground(boolean on) {
		this.properties.setProperty(EMFGraphics2D.BACKGROUND, on);
	}

	@Override
	public void setPropertyBackgroundColor(Color color) {
		this.properties.setProperty(EMFGraphics2D.BACKGROUND_COLOR, color);
	}

	@Override
	public void setPropertyTransparent(boolean on) {
		this.properties.setProperty(EMFGraphics2D.TRANSPARENT, on);
	}
}
