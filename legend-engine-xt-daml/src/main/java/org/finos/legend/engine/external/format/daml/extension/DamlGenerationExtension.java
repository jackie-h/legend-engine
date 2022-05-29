package org.finos.legend.engine.external.format.daml.extension;

import org.eclipse.collections.api.RichIterable;
import org.finos.legend.engine.external.format.daml.DamlGenerationService;
import org.finos.legend.engine.external.format.daml.generation.model.DamlGenerationConfig;
import org.finos.legend.engine.external.format.daml.generation.model.DamlGenerationConfigFromFileGenerationSpecificationBuilder;
import org.finos.legend.engine.external.shared.format.extension.GenerationExtension;
import org.finos.legend.engine.external.shared.format.extension.GenerationMode;
import org.finos.legend.engine.external.shared.format.generations.description.FileGenerationDescription;
import org.finos.legend.engine.external.shared.format.generations.description.GenerationConfigurationDescription;
import org.finos.legend.engine.external.shared.format.generations.description.GenerationProperty;
import org.finos.legend.engine.external.shared.format.imports.description.ImportConfigurationDescription;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.CompileContext;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.language.pure.modelManager.ModelManager;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.PackageableElement;
import org.finos.legend.engine.protocol.pure.v1.model.packageableElement.fileGeneration.FileGenerationSpecification;
import org.finos.legend.pure.generated.Root_meta_pure_generation_metamodel_GenerationConfiguration;
import org.finos.legend.pure.generated.Root_meta_pure_generation_metamodel_GenerationOutput;
import org.finos.legend.pure.generated.core_external_language_daml_integration;
import org.finos.legend.pure.generated.core_external_language_daml_transformation;

import java.util.ArrayList;
import java.util.List;

public class DamlGenerationExtension implements GenerationExtension
{
    @Override
    public String getLabel()
    {
        return "DAML";
    }

    @Override
    public String getKey()
    {
        return "daml";
    }


    @Override
    public GenerationMode getMode()
    {
        return GenerationMode.Schema;
    }

    @Override
    public GenerationConfigurationDescription getGenerationDescription()
    {
        return new GenerationConfigurationDescription()
        {
            @Override
            public String getLabel()
            {
                return DamlGenerationExtension.this.getLabel();
            }

            @Override
            public String getKey()
            {
                return DamlGenerationExtension.this.getKey();
            }

            @Override
            public List<GenerationProperty> getProperties(PureModel pureModel)
            {
                return FileGenerationDescription.extractGenerationProperties(core_external_language_daml_integration.Root_meta_external_language_daml_generation_describeConfiguration__GenerationParameter_MANY_(pureModel.getExecutionSupport()));
            }
        };
    }

    @Override
    public ImportConfigurationDescription getImportDescription()
    {
        return null;
    }

    @Override
    public Object getService(ModelManager modelManager)
    {
        return new DamlGenerationService(modelManager);
    }

    @Override
    public List<Root_meta_pure_generation_metamodel_GenerationOutput> generateFromElement(PackageableElement element, CompileContext compileContext)
    {
        if(element instanceof FileGenerationSpecification) {
            FileGenerationSpecification specification = (FileGenerationSpecification) element;
            DamlGenerationConfig generationConfig = DamlGenerationConfigFromFileGenerationSpecificationBuilder.build(specification);
            RichIterable<? extends Root_meta_pure_generation_metamodel_GenerationOutput> output = core_external_language_daml_transformation.Root_meta_external_language_daml_generation_generateDamlFromPure_DamlConfig_1__GenerationOutput_MANY_(generationConfig.transformToPure(compileContext.pureModel), compileContext.pureModel.getExecutionSupport());
            return new ArrayList<>(output.toList());
        }
        return null;
    }

    @Override
    public Root_meta_pure_generation_metamodel_GenerationConfiguration defaultConfig(CompileContext context)
    {
        return core_external_language_daml_integration.Root_meta_external_language_daml_generation_defaultConfig__DamlConfig_1_(context.pureModel.getExecutionSupport());
    }
}