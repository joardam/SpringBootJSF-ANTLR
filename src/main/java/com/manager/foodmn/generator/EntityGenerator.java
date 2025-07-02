package com.manager.foodmn.generator;

import com.manager.foodmn.antlr.EntityDefBaseVisitor;
import com.manager.foodmn.antlr.EntityDefLexer;
import com.manager.foodmn.antlr.EntityDefParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class EntityGenerator {

    public static void main(String[] args) throws IOException {
        String sourceDirectory = "src/main/resources/entities";
        System.out.println("Procurando por arquivos .ent em: " + sourceDirectory);

        try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".ent"))
                    .forEach(EntityGenerator::generateEntityFromFile);
        }
    }

    private static void generateEntityFromFile(Path entityFile) {
        try {
            System.out.println("Processando arquivo: " + entityFile.getFileName());

            CharStream cs = CharStreams.fromPath(entityFile);
            EntityDefLexer lexer = new EntityDefLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            EntityDefParser parser = new EntityDefParser(tokens);
            ParseTree tree = parser.entityFile();

            EntityVisitor visitor = new EntityVisitor();
            visitor.visit(tree);

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo " + entityFile.getFileName());
            e.printStackTrace();
        }
    }

    static class EntityVisitor extends EntityDefBaseVisitor<Void> {
        @Override
        public Void visitEntityDef(EntityDefParser.EntityDefContext ctx) {
            String className = ctx.ID().getText();
            String entityFolderName = className.toLowerCase();

            generateModel(className, entityFolderName, ctx.fieldDef());
            generateRepository(className, entityFolderName);
            generateServiceInterface(className, entityFolderName);
            generateServiceImplementation(className, entityFolderName);
            generateController(className, entityFolderName);
            //generateView(className, entityFolderName, ctx.fieldDef());

            return null;
        }

        private void generateModel(String className, String entityFolderName, List<EntityDefParser.FieldDefContext> fields) {
            StringBuilder sb = new StringBuilder();

            sb.append("package com.manager.foodmn.").append(entityFolderName).append(".model;\n\n");

            sb.append("import jakarta.persistence.Entity;\n");
            sb.append("import jakarta.persistence.Id;\n");
            sb.append("import lombok.Data;\n");
            sb.append("import java.math.BigDecimal;\n");
            sb.append("import jakarta.persistence.GeneratedValue;\n");
            sb.append("import jakarta.persistence.GenerationType;\n\n");

            sb.append("@Data\n");
            sb.append("@Entity\n");
            sb.append("public class ").append(className).append(" {\n\n");

            boolean isFirstField = true;
            for (EntityDefParser.FieldDefContext fieldCtx : fields) {
                if (isFirstField) {
                    sb.append("    @Id\n");
                    sb.append("    @GeneratedValue(strategy = GenerationType.SEQUENCE)\n");
                    sb.append("    private Long id;\n\n");
                    isFirstField = false;
                }
                String type = fieldCtx.type().getText();
                String name = fieldCtx.ID().getText();
                sb.append("    private ").append(type).append(" ").append(name).append(";\n\n");
            }

            sb.append("}\n");

            Path outFile = Paths.get("src/main/java/com/manager/foodmn/" + entityFolderName + "/model/" + className + ".java");
            try {
                Files.createDirectories(outFile.getParent());
                Files.writeString(outFile, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Classe de modelo gerada com sucesso: " + outFile);
            } catch (IOException e) {
                System.err.println("Erro ao gerar a classe de modelo para " + className);
                e.printStackTrace();
            }
        }

        private void generateRepository(String className, String entityFolderName) {
            StringBuilder sb = new StringBuilder();
            String repositoryClassName = className + "Repository";

            sb.append("package com.manager.foodmn.").append(entityFolderName).append(".repository;\n\n");

            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".model.").append(className).append(";\n");
            sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n\n");

            sb.append("public interface ").append(repositoryClassName).append(" extends JpaRepository<").append(className).append(", Long> {\n");
            sb.append("\n}\n");

            Path outFile = Paths.get("src/main/java/com/manager/foodmn/" + entityFolderName + "/repository/" + repositoryClassName + ".java");
            try {
                Files.createDirectories(outFile.getParent());
                Files.writeString(outFile, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Interface de repositório gerada com sucesso: " + outFile);
            } catch (IOException e) {
                System.err.println("Erro ao gerar a interface de repositório para " + className);
                e.printStackTrace();
            }
        }

        private void generateServiceInterface(String className, String entityFolderName) {
            StringBuilder sb = new StringBuilder();
            String serviceInterfaceName = className + "Service";
            String modelVariableName = className.substring(0, 1).toLowerCase() + className.substring(1);

            sb.append("package com.manager.foodmn.").append(entityFolderName).append(".service;\n\n");

            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".model.").append(className).append(";\n");
            sb.append("import java.util.List;\n");
            sb.append("import java.util.Optional;\n\n");

            sb.append("public interface ").append(serviceInterfaceName).append(" {\n\n");

            sb.append("    ").append(className).append(" save(").append(className).append(" ").append(modelVariableName).append(");\n\n");
            sb.append("    List<").append(className).append("> findAll();\n\n");
            sb.append("    Optional<").append(className).append("> findById(Long id);\n\n");
            sb.append("    ").append(className).append(" update(").append(className).append(" ").append(modelVariableName).append(");\n\n");
            sb.append("    void deleteById(Long id);\n\n");
            sb.append("    void deletebyObject(").append(className).append(" ").append(modelVariableName).append(");\n");

            sb.append("}\n");

            Path outFile = Paths.get("src/main/java/com/manager/foodmn/" + entityFolderName + "/service/" + serviceInterfaceName + ".java");
            try {
                Files.createDirectories(outFile.getParent());
                Files.writeString(outFile, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Interface de serviço gerada com sucesso: " + outFile);
            } catch (IOException e) {
                System.err.println("Erro ao gerar a interface de serviço para " + className);
                e.printStackTrace();
            }
        }
        private void generateServiceImplementation(String className, String entityFolderName) {
            StringBuilder sb = new StringBuilder();
            String serviceInterfaceName = className + "Service";
            String serviceImplName = className + "ServiceImpl";
            String repositoryClassName = className + "Repository";
            String repositoryVariableName = repositoryClassName.substring(0, 1).toLowerCase() + repositoryClassName.substring(1);
            String modelVariableName = className.substring(0, 1).toLowerCase() + className.substring(1);

            sb.append("package com.manager.foodmn.").append(entityFolderName).append(".service;\n\n");

            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".model.").append(className).append(";\n");
            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".repository.").append(repositoryClassName).append(";\n");
            sb.append("import org.springframework.stereotype.Service;\n");
            sb.append("import java.util.List;\n");
            sb.append("import java.util.Optional;\n\n");

            sb.append("@Service\n");
            sb.append("public class ").append(serviceImplName).append(" implements ").append(serviceInterfaceName).append("{\n\n");

            sb.append("    private final ").append(repositoryClassName).append(" ").append(repositoryVariableName).append(";\n\n");

            sb.append("    public ").append(serviceImplName).append("(").append(repositoryClassName).append(" ").append(repositoryVariableName).append(") {\n");
            sb.append("        this.").append(repositoryVariableName).append(" = ").append(repositoryVariableName).append(";\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public ").append(className).append(" save(").append(className).append(" ").append(modelVariableName).append(") {\n");
            sb.append("        return ").append(repositoryVariableName).append(".save(").append(modelVariableName).append(");\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public List<").append(className).append("> findAll() {\n");
            sb.append("        return ").append(repositoryVariableName).append(".findAll();\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public Optional<").append(className).append("> findById(Long id) {\n");
            sb.append("        return ").append(repositoryVariableName).append(".findById(id);\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public ").append(className).append(" update(").append(className).append(" ").append(modelVariableName).append(") {\n");
            sb.append("        return ").append(repositoryVariableName).append(".save(").append(modelVariableName).append(");\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public void deleteById(Long id) {\n");
            sb.append("        ").append(repositoryVariableName).append(".deleteById(id);\n");
            sb.append("    }\n\n");

            sb.append("    @Override\n");
            sb.append("    public void deletebyObject(").append(className).append(" ").append(modelVariableName).append("){\n");
            sb.append("        ").append(repositoryVariableName).append(".delete(").append(modelVariableName).append(");\n");
            sb.append("    }\n\n");

            sb.append("}\n");

            Path outFile = Paths.get("src/main/java/com/manager/foodmn/" + entityFolderName + "/service/" + serviceImplName + ".java");
            try {
                Files.createDirectories(outFile.getParent());
                Files.writeString(outFile, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Classe de implementação de serviço gerada com sucesso: " + outFile);
            } catch (IOException e) {
                System.err.println("Erro ao gerar a classe de implementação de serviço para " + className);
                e.printStackTrace();
            }
        }

        private void generateController(String className, String entityFolderName) {
            StringBuilder sb = new StringBuilder();
            String controllerClassName = className + "Controller";
            String serviceInterfaceName = className + "Service";
            String serviceVariableName = serviceInterfaceName.substring(0, 1).toLowerCase() + serviceInterfaceName.substring(1);
            String instanceVariableName = className.toLowerCase();
            String listVariableName = instanceVariableName + "s";

            sb.append("package com.manager.foodmn.").append(entityFolderName).append(".controller;\n\n");

            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".model.").append(className).append(";\n");
            sb.append("import com.manager.foodmn.").append(entityFolderName).append(".service.").append(serviceInterfaceName).append(";\n");
            sb.append("import jakarta.annotation.PostConstruct;\n");
            sb.append("import jakarta.faces.view.ViewScoped;\n");
            sb.append("import jakarta.inject.Inject;\n");
            sb.append("import jakarta.inject.Named;\n");
            sb.append("import lombok.Data;\n");
            sb.append("import java.util.List;\n\n");

            sb.append("@Named\n");
            sb.append("@Data\n");
            sb.append("@ViewScoped\n");
            sb.append("public class ").append(controllerClassName).append(" {\n\n");

            sb.append("    @Inject\n");
            sb.append("    private ").append(serviceInterfaceName).append(" ").append(serviceVariableName).append(";\n\n");

            sb.append("    private List<").append(className).append(">  ").append(listVariableName).append(";\n");
            sb.append("    private ").append(className).append(" ").append(instanceVariableName).append(" = new ").append(className).append("();\n\n");

            sb.append("    private boolean editMode = false;\n\n");

            sb.append("    @PostConstruct\n");
            sb.append("    public void init() {\n");
            sb.append("        this.").append(listVariableName).append(" = ").append(serviceVariableName).append(".findAll();\n");
            sb.append("    }\n\n");

            sb.append("    public void save() {\n");
            sb.append("        ").append(serviceVariableName).append(".save(this.").append(instanceVariableName).append(");\n");
            sb.append("        if(!editMode){\n");
            sb.append("            ").append(listVariableName).append(".add(this.").append(instanceVariableName).append(");\n");
            sb.append("        }\n");
            sb.append("        this.").append(instanceVariableName).append(" = new ").append(className).append("();\n");
            sb.append("        this.editMode = false;\n");
            sb.append("    }\n\n");

            sb.append("    public void delete(").append(className).append(" ").append(instanceVariableName).append("){\n");
            sb.append("        ").append(serviceVariableName).append(".deletebyObject(").append(instanceVariableName).append(");\n");
            sb.append("        ").append(listVariableName).append(".remove(").append(instanceVariableName).append(");\n");
            sb.append("    }\n\n");

            sb.append("    public void edit(").append(className).append(" ").append(instanceVariableName).append("){\n");
            sb.append("        this.editMode = true;\n");
            sb.append("        this.").append(instanceVariableName).append(" = ").append(instanceVariableName).append(";\n");
            sb.append("    }\n\n");

            sb.append("    public void cancel(){\n");
            sb.append("        this.editMode = false;\n");
            sb.append("        this.").append(instanceVariableName).append(" = new ").append(className).append("();\n");
            sb.append("    }\n\n");

            sb.append("}\n");

            Path outFile = Paths.get("src/main/java/com/manager/foodmn/" + entityFolderName + "/controller/" + controllerClassName + ".java");
            try {
                Files.createDirectories(outFile.getParent());
                Files.writeString(outFile, sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Classe de controller gerada com sucesso: " + outFile);
            } catch (IOException e) {
                System.err.println("Erro ao gerar a classe de controller para " + className);
                e.printStackTrace();
            }
        }

    }
}