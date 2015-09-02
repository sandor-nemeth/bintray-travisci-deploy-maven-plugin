import org.codehaus.plexus.util.FileUtils

File deployDescriptor = new File( basedir, "target/bintray-deploy.json" );
File expected = new File(basedir, "expected.json")

assert deployDescriptor.isFile()

assert FileUtils.contentEquals(expected, deployDescriptor)