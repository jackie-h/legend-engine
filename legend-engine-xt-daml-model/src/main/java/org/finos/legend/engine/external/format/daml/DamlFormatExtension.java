//  Copyright 2022 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

package org.finos.legend.engine.external.format.daml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.finos.legend.engine.external.format.daml.fromModel.ModelToDamlConfiguration;
import org.finos.legend.engine.external.shared.format.model.compile.ExternalSchemaCompileContext;
import org.finos.legend.engine.external.shared.format.model.transformation.fromModel.ExternalFormatSchemaGenerationExtension;
import org.finos.legend.engine.language.pure.compiler.toPureGraph.PureModel;
import org.finos.legend.engine.protocol.haskell.metamodel.HaskellModule;
import org.finos.legend.engine.protocol.haskell.metamodel.Translator;
import org.finos.legend.pure.generated.Root_meta_external_language_daml_transformation_fromPure_ModelToDamlConfiguration_Impl;
import org.finos.legend.pure.generated.Root_meta_external_language_haskell_metamodel_HaskellSchema;
import org.finos.legend.pure.generated.Root_meta_external_language_haskell_metamodel_HaskellSchema_Impl;
import org.finos.legend.pure.generated.Root_meta_external_shared_format_ExternalFormatContract;
import org.finos.legend.pure.generated.Root_meta_external_shared_format_transformation_fromPure_ModelToSchemaConfiguration;
import org.finos.legend.pure.generated.core_external_language_daml_externalFormatContract;
import org.finos.legend.pure.generated.core_external_language_daml_serialization;

public class DamlFormatExtension implements ExternalFormatSchemaGenerationExtension<Root_meta_external_language_haskell_metamodel_HaskellSchema, ModelToDamlConfiguration>
{
    private static final Root_meta_external_shared_format_ExternalFormatContract<Root_meta_external_language_haskell_metamodel_HaskellSchema> damlContract = (Root_meta_external_shared_format_ExternalFormatContract<Root_meta_external_language_haskell_metamodel_HaskellSchema>) core_external_language_daml_externalFormatContract.Root_meta_external_language_daml_contract_damlFormatContract__ExternalFormatContract_1_(PureModel.CORE_PURE_MODEL.getExecutionSupport());

    public static final String TYPE = damlContract._id();


    @Override
    public Root_meta_external_shared_format_ExternalFormatContract<Root_meta_external_language_haskell_metamodel_HaskellSchema> getExternalFormatContract()
    {
        return damlContract;
    }

    @Override
    public Root_meta_external_language_haskell_metamodel_HaskellSchema compileSchema(ExternalSchemaCompileContext context)
    {
        try
        {
            return new Root_meta_external_language_haskell_metamodel_HaskellSchema_Impl("")
                    ._module(
                            new Translator().translate(
                                    new ObjectMapper().readValue(context.getContent(), HaskellModule.class),
                                    context.getPureModel()
                            )
                    );
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String metamodelToText(Root_meta_external_language_haskell_metamodel_HaskellSchema schemaDetail, PureModel pureModel)
    {
        //todo: use the Java version instead, but needs a transformer to be generated
        return core_external_language_daml_serialization.Root_meta_external_language_daml_format_toString_HaskellModule_1__String_1_(schemaDetail._module(), pureModel.getExecutionSupport());
    }

    @Override
    public Root_meta_external_shared_format_transformation_fromPure_ModelToSchemaConfiguration compileModelToSchemaConfiguration(ModelToDamlConfiguration configuration, PureModel pureModel)
    {
        return new Root_meta_external_language_daml_transformation_fromPure_ModelToDamlConfiguration_Impl("", null, pureModel.getClass("meta::external::language::daml::transformation::fromPure::ModelToDamlConfiguration"))
                ._targetSchemaSet(configuration.targetSchemaSet);
    }
}
