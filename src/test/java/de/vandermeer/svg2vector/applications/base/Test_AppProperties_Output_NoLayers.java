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

package de.vandermeer.svg2vector.applications.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import de.vandermeer.execs.options.ExecS_CliParser;
import de.vandermeer.svg2vector.converters.SvgTargets;
import de.vandermeer.svg2vector.loaders.StandardLoader;

/**
 * Tests for {@link AppProperties} - file output options.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v2.0.0-SNAPSHOT build 170411 (11-Apr-17) for Java 1.8
 * @since      v2.0.0
 */
public class Test_AppProperties_Output_NoLayers {

	@Test
	public void test_WarningDout(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-d", "foo"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertEquals(1, props.getWarnings().size());
		assertEquals("no layers processed but CLI option <output-directory> used, will be ignored", props.getWarnings().get(0));
	}

	@Test
	public void test_WarningNoBN(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"--fout-no-basename"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertEquals(1, props.getWarnings().size());
		assertEquals("no layers processed but CLI option <fout-no-basename> used, will be ignored", props.getWarnings().get(0));
	}

	@Test
	public void test_WarningUseBN(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"--use-basename", "bn"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertEquals(1, props.getWarnings().size());
		assertEquals("no layers processed but CLI option <use-basename> used, will be ignored", props.getWarnings().get(0));
	}

	@Test
	public void test_WarningIndex(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-i"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertEquals(1, props.getWarnings().size());
		assertEquals("no layers processed but CLI option <fout-layer-index> used, will be ignored", props.getWarnings().get(0));
	}

	@Test
	public void test_WarningID(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-I"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertEquals(1, props.getWarnings().size());
		assertEquals("no layers processed but CLI option <fout-layer-id> used, will be ignored", props.getWarnings().get(0));
	}

	@Test
	public void test_FoutBlank(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", ""
		};

		assertEquals(null, cli.parse(args));
		assertEquals(-1, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output filename is blank", err);
	}

	@Test
	public void test_FoutExt(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "foo.pdf"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output filename <foo.pdf> should not contain target file extension", err);
	}

	@Test
	public void test_SameAsTarget(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.svg}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "svg",
				"-f", "src/test/resources/svg-files/simple.svg",
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("no output name given and target extension same as input extension, do not want to overwrite input file", err);
	}

	@Test
	public void test_FoutExistsNoOverwrite(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "target/output-tests/app-props/fout-exists"
		};

		File dir = new File("target/output-tests/app-props");
		dir.mkdirs();
		File f = new File("target/output-tests/app-props/fout-exists.pdf");
		try {
			f.createNewFile();
		}
		catch (IOException ignore) {}

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output file <target/output-tests/app-props/fout-exists.pdf> exists and no option <overwrite-existing> used", err);

		f.delete();
	}

	@Test
	public void test_FoutExistsIsdir(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "target/output-tests/app-props/fout-exists"
		};

		File dir = new File("target/output-tests/app-props/fout-exists.pdf");
		dir.mkdirs();

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output file <target/output-tests/app-props/fout-exists.pdf> exists but is a directory", err);

		dir.delete();
	}

	@Test
	public void test_FoutExistsCantWrite(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"--overwrite-existing",
				"-o", "target/output-tests/app-props/fout-exists"
		};

		File dir = new File("target/output-tests/app-props");
		dir.mkdirs();
		File f = new File("target/output-tests/app-props/fout-exists.pdf");
		try {
			f.createNewFile();
			f.setWritable(false);
		}
		catch (IOException ignore) {}

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output file <target/output-tests/app-props/fout-exists.pdf> exists but cannot write to it", err);

		f.delete();
	}

	@Test
	public void test_ParrentNoDir(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "target/output-tests/app-props/test/file"
		};

		File dir = new File("target/output-tests/app-props");
		dir.mkdirs();
		File f = new File("target/output-tests/app-props/test");
		try {
			f.createNewFile();
			f.setWritable(false);
		}
		catch (IOException ignore) {}

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output directory <target/output-tests/app-props/test> exists but is not a directory", err);

		f.delete();
	}

	@Test
	public void test_NoParrentNoCreate(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "target/output-tests/app-props/test/file"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		String err = props.setOutput();
		assertNotNull(err);
		assertEquals("output directory <target/output-tests/app-props/test> does not exist and CLI option <create-directories> not used", err);
	}

	@Test
	public void test_DoesNoLayers(){
		ExecS_CliParser cli = new ExecS_CliParser();
		AppProperties<StandardLoader> props = new AppProperties<StandardLoader>(new SvgTargets[]{SvgTargets.pdf}, new StandardLoader());
		cli.addAllOptions(props.getAppOptions());
		String[] args = new String[]{
				"-t", "pdf",
				"-f", "src/test/resources/svg-files/chomsky-hierarchy.svgz",
				"-o", "target/output-tests/app-props/test/file",
				"--create-directories"
		};

		assertEquals(null, cli.parse(args));
		assertEquals(0, Test_AppProperties.setCli4Options(cli.getCommandLine(), props.getAppOptions()));

		assertNull(props.setInput());
		assertNull(props.setOutput());
		assertTrue(props.doesNoLayers());
		assertFalse(props.doesLayers());
	}
}
