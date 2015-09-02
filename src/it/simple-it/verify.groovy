import com.fasterxml.jackson.databind.ObjectMapper

File deployDescriptor = new File( basedir, "target/bintray-deploy.json" );

assert deployDescriptor.isFile()

ObjectMapper objectMapper = new ObjectMapper();
def tree = objectMapper.readTree(deployDescriptor);
assert tree.get("package") != null;

