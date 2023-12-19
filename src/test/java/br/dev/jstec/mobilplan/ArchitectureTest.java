package br.dev.jstec.mobilplan;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {

    @Test
    public void domainNotDependByInfrastructure() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("br.dev.jstec.mobilplan");

        ArchRule rule = noClasses()
                .that().resideInAPackage("br.dev.jstec.mobilplan.domain")
                .should().dependOnClassesThat()
                .resideInAPackage("br.dev.jstec.mobilplan.infrastructure");

        rule.check(importedClasses);
    }
    @Test
    public void domainNotDependByApplication() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("br.dev.jstec.mobilplan");

        ArchRule rule = noClasses()
                .that().resideInAPackage("br.dev.jstec.mobilplan.domain")
                .should().dependOnClassesThat()
                .resideInAPackage("br.dev.jstec.mobilplan.application");

        rule.check(importedClasses);
    }

    @Test
    public void applicationDependOnDomainButNotDependByInfrastructure() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("br.dev.jstec.mobilplan");

        ArchRule dominioRule = noClasses()
                .that().resideInAPackage("br.dev.jstec.mobilplan.domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("br.dev.jstec.mobilplan.application..");

        ArchRule infraRule = noClasses()
                .that().resideInAPackage("br.dev.jstec.mobilplan.application..")
                .should().dependOnClassesThat()
                .resideInAPackage("br.dev.jstec.mobilplan.infrastructure..");

        dominioRule.check(importedClasses);
        infraRule.check(importedClasses);
    }

}
