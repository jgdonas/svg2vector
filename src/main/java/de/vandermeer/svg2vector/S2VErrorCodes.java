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

package de.vandermeer.svg2vector;

import java.util.TreeMap;

import org.apache.commons.lang3.text.StrBuilder;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import de.vandermeer.execs.ExecS_Application;
import de.vandermeer.execs.options.ApplicationOption;
import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.svg2vector.applications.core.ErrorCodeCategories;
import de.vandermeer.svg2vector.applications.core.ErrorCodes;

/**
 * Simple application that prints all S2V application error codes.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v2.1.0-SNAPSHOT build 170420 (20-Apr-17) for Java 1.8
 * @since      v2.1.0
 */
public class S2VErrorCodes implements ExecS_Application {

	/** Application name. */
	public final static String APP_NAME = "s2v-aec";

	/** Application display name. */
	public final static String APP_DISPLAY_NAME = "Svg2Vector Application Error Codes";

	/** Application version, should be same as the version in the class JavaDoc. */
	public final static String APP_VERSION = "v2.1.0-SNAPSHOT build 170420 (20-Apr-17) for Java 1.8";

	/** CLI parser. */
	final private ExecS_CliParser cli;

	public S2VErrorCodes(){
		this.cli = new ExecS_CliParser();
	}

	@Override
	public int executeApplication(String[] args){
		// parse command line, exit with help screen if error
		final int ret = ExecS_Application.super.executeApplication(args);
		if(ret!=0){
			return ret;
		}

//		this.printEcList("de/vandermeer/svg2vector/applications/aec/console.stg");
//		this.printEcList("de/vandermeer/svg2vector/applications/aec/adoc.stg");

//		this.printCatList("de/vandermeer/svg2vector/applications/aec/console.stg");
//		this.printCatList("de/vandermeer/svg2vector/applications/aec/adoc.stg");

		this.fullTable("de/vandermeer/svg2vector/applications/aec/console.stg");
//		this.fullTable("de/vandermeer/svg2vector/applications/aec/adoc.stg");

		return 0;
	}

	/**
	 * Prints list of error codes.
	 * @param stgFilename the STG file name
	 */
	public void printEcList(String stgFilename){
		STGroupFile stg = new STGroupFile(stgFilename);
		ST ecList = stg.getInstanceOf("ecList");

		TreeMap<Integer, ErrorCodes> ecMap = this.ecMap(null);
		for(ErrorCodes ec : ecMap.values()){
			ST ecST = stg.getInstanceOf("ecListEntry");
			ecST.add("code", ec.getCode());
			ecST.add("description", ec.getDescription());
			ecST.add("message", ec.getMessage());

			String padding = "  " + ec.getCode() + " -> ";
			new StrBuilder().appendPadding(padding.length(), ' ');
			ecST.add("padding", new StrBuilder().appendPadding(padding.length(), ' ').toString());

			ecList.add("aecs", ecST.render());
		}

		System.out.println(ecList.render());
	}

	/**
	 * Prints list of error categories.
	 * @param stgFilename the STG file name
	 */
	public void printCatList(String stgFilename){
		STGroupFile stg = new STGroupFile(stgFilename);
		ST cats = stg.getInstanceOf("catList");

		TreeMap<Integer, ErrorCodeCategories> catMap = this.catMap();
		for(ErrorCodeCategories cat : catMap.values()){
			ST catST = stg.getInstanceOf("catListEntry");
			catST.add("cat", cat.getDescription());
			catST.add("start", cat.getStart());
			catST.add("end", cat.getEnd());
			cats.add("cats", catST.render());
		}

		System.out.println(cats.render());
	}

	/**
	 * Prints error codes in tabular form.
	 * @param stgFilename the STG file name
	 */
	public void fullTable(String stgFilename){
		STGroupFile stg = new STGroupFile(stgFilename);
		ST cats = stg.getInstanceOf("catTable");

		TreeMap<Integer, ErrorCodeCategories> catMap = this.catMap();

		for(ErrorCodeCategories cat : catMap.values()){
			ST catST = stg.getInstanceOf("catTableEntry");
			catST.add("cat", cat.getDescription());
			catST.add("start", cat.getStart());
			catST.add("end", cat.getEnd());

			TreeMap<Integer, ErrorCodes> ecMap = this.ecMap(cat);
			for(ErrorCodes ec : ecMap.values()){
				ST ecST = stg.getInstanceOf("ecTableEntry");
				ecST.add("code", ec.getCode());
				ecST.add("description", ec.getDescription());
				ecST.add("message", ec.getMessage());

				String padding = "  " + ec.getCode() + " -> ";
				new StrBuilder().appendPadding(padding.length(), ' ');
				ecST.add("padding", new StrBuilder().appendPadding(padding.length(), ' ').toString());

				catST.add("aecs", ecST.render());
			}
			cats.add("cats", catST.render());
		}

		System.out.println(cats.render());
	}

	/**
	 * Returns a sorted map of all categories.
	 * @return sorted map
	 */
	protected TreeMap<Integer, ErrorCodeCategories> catMap(){
		TreeMap<Integer, ErrorCodeCategories> catMap = new TreeMap<>();
		for(ErrorCodeCategories cat : ErrorCodeCategories.values()){
			catMap.put(0-cat.getStart(), cat);
		}
		return catMap;
	}

	/**
	 * Returns a sorted map of all error codes.
	 * @param category an optional catagory to select error codes for
	 * @return sorted map, empty if no error codes found
	 */
	protected TreeMap<Integer, ErrorCodes> ecMap(ErrorCodeCategories category){
		TreeMap<Integer, ErrorCodes> ecMap = new TreeMap<>();
		for(ErrorCodes ec : ErrorCodes.values()){
			if(category==null || ec.getCategory()==category){
				ecMap.put(0-ec.getCode(), ec);
			}
		}
		return ecMap;
	}

	@Override
	public String getAppName() {
		return APP_NAME;
	}

	@Override
	public String getAppDisplayName(){
		return APP_DISPLAY_NAME;
	}

	@Override
	public String getAppDescription() {
		return "Simply prints a list of all S2V application error codes for reference";
	}

	@Override
	public String getAppVersion() {
		return APP_VERSION;
	}

	@Override
	public ApplicationOption<?>[] getAppOptions() {
		return null;
	}

	@Override
	public ExecS_CliParser getCli() {
		return this.cli;
	}
}
