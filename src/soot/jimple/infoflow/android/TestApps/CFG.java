package soot.jimple.infoflow.android.TestApps;

import java.io.IOException;
import java.util.Collections;
import org.xmlpull.v1.XmlPullParserException;
import soot.PackManager;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.infoflow.android.SetupApplication;
import soot.options.Options;

import soot.jimple.toolkits.callgraph.*;

public class CFG {

	public CFG() {

		// TODO Auto-generated constructor stub

	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		String droidPlatform = args[1];
		String apk2bAnalyzed = args[0];
		String srcSinkFile = args[2];
		SetupApplication app = new SetupApplication(droidPlatform, apk2bAnalyzed);

		try {

			app.calculateSourcesSinksEntrypoints(srcSinkFile);

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (XmlPullParserException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		soot.G.reset();

		Options.v().set_src_prec(Options.src_prec_apk);

		Options.v().set_process_dir(Collections.singletonList(apk2bAnalyzed));

		Options.v().set_android_jars(droidPlatform);

		Options.v().set_whole_program(true);

		Options.v().set_allow_phantom_refs(true);

		Options.v().set_output_format(Options.output_format_none);

		Options.v().setPhaseOption("cg.spark", "on");
		Options.v().setPhaseOption("jap.zwfpc", "on");

		Scene.v().loadNecessaryClasses(); 

		SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();

		Options.v().set_main_class(entryPoint.getSignature());

		Scene.v().setEntryPoints(Collections.singletonList(entryPoint));

		System.out.println(entryPoint.getActiveBody());

		PackManager.v().runPacks();

		CallGraph cg = Scene.v().getCallGraph();
		System.out.println(cg.size());
		System.out.println(cg.toString());

	}

}
