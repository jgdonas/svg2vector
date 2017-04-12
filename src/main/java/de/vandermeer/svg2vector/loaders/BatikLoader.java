/* Copyright 2017 Sven van der Meer <vdmeer.sven@mykolab.com>
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

package de.vandermeer.svg2vector.loaders;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.vandermeer.svg2vector.applications.base.SV_DocumentLoader;

/**
 * Loads an SVG document using Batik and provides some methods to deal with layers.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v2.0.0-SNAPSHOT build 170411 (11-Apr-17) for Java 1.8
 * @since      v2.0.0
 */
public class BatikLoader extends SV_DocumentLoader {

	/** Local bridge context. */
	protected BridgeContext bridgeContext;

	/** SVG document object. */
	protected Document svgDocument;

	/** Size value. */
	protected Dimension size;

	/** Mapping from node id to actual DOM node. */
	protected final Map<String, Node> layerNodes = new HashMap<>();

	/**
	 * Returns the Inkscape label for a given node.
	 * @param node XML/SVG node
	 * @return null if node was null or no Inkscape label found, label otherwise
	 */
	public static String getLabel(Node node){
		if(node==null){
			return null;
		}

		NamedNodeMap nnm = node.getAttributes();
		for(int i=0; i<nnm.getLength(); i++){
			if("inkscape:label".equals(nnm.item(i).getNodeName())){
				return nnm.item(i).getNodeValue();
			}
		}
		return null;
	}

	/**
	 * Returns the Inkscape index (actual id with layer removed) for a given node.
	 * @param node XML/SVG node
	 * @return 0 if node was null or no IDs found, index otherwise
	 */
	static int getIndex(Node node){
		if(node==null){
			return 0;
		}

		NamedNodeMap nnm = node.getAttributes();
		for(int i=0; i<nnm.getLength(); i++){
			if("id".equals(nnm.item(i).getNodeName())){
				String index = nnm.item(i).getNodeValue();
				index = StringUtils.substringAfter(index, "layer");
				return new Integer(index);
			}
		}
		return 0;
	}

	@Override
	public String load(String fn) {
		Validate.notBlank(fn);

		if(!this.isLoaded){
			this.bridgeContext = null;
			this.svgDocument = null;

			UserAgent userAgent = new UserAgentAdapter();
			DocumentLoader documentLoader = new DocumentLoader(userAgent);

			this.bridgeContext = new BridgeContext(userAgent, documentLoader);
			this.bridgeContext.setDynamic(true);

			try{
				this.svgDocument = documentLoader.loadDocument(fn);
			}
			catch(Exception ex){
				this.bridgeContext = null;
				this.svgDocument = null;
				return this.getClass().getSimpleName() + ": exception loading svgDocument - " + ex.getMessage();
			}
			documentLoader.dispose();

			Element elem = this.svgDocument.getDocumentElement();
			this.size = new Dimension();
			try{
				this.size.setSize(Double.valueOf(elem.getAttribute("width")), Double.valueOf(elem.getAttribute("height")));
			}
			catch(Exception ex){
				this.bridgeContext = null;
				this.svgDocument = null;
				this.size = null;
				return this.getClass().getSimpleName() + ": exception setting docucment size - " + ex.getMessage();
			}

			NodeList nodes = elem.getChildNodes();
			if(nodes!=null){
				for(int i=0; i<nodes.getLength(); i++){
					if("g".equals(nodes.item(i).getNodeName())){
						NamedNodeMap nnm = nodes.item(i).getAttributes();
						for(int node=0; node<nnm.getLength(); node++){
							if("inkscape:groupmode".equals(nnm.item(node).getNodeName())){
								String id = BatikLoader.getLabel(nodes.item(i));
								this.layers.put(id, BatikLoader.getIndex(nodes.item(i)));
								this.layerNodes.put(id, nodes.item(i));
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void switchOnAllLayers() {
		for(Node node : this.layerNodes.values()){
			NamedNodeMap nnm = node.getAttributes();
			for(int i=0; i<nnm.getLength(); i++){
				if("style".equals(nnm.item(i).getNodeName())){
					nnm.item(i).setNodeValue("display:inline");
					break;
				}
			}
		}
	}

	@Override
	public void switchOffAllLayers() {
		for(Node node : this.layerNodes.values()){
			NamedNodeMap nnm = node.getAttributes();
			for(int i=0; i<nnm.getLength(); i++){
				if("style".equals(nnm.item(i).getNodeName())){
					nnm.item(i).setNodeValue("display:none");
					break;
				}
			}
		}
	}

	@Override
	public void switchOnLayer(String layer) {
		if(StringUtils.isBlank(layer)){
			return;
		}

		Node node = this.layerNodes.get(layer);
		if(node==null){
			return;
		}

		NamedNodeMap nnm = node.getAttributes();
		for(int i=0; i<nnm.getLength(); i++){
			if("style".equals(nnm.item(i).getNodeName())){
				nnm.item(i).setNodeValue("display:inline");
				return;
			}
		}
	}

//	/**
//	 * Returns the loader's document.
//	 * @return loaded document, null if none loaded
//	 */
//	public Document getDocument() {
//		return this.svgDocument;
//	}

//	/** List of SVG nodes in document. */
//	protected NodeList svgNodeList;
//

}
