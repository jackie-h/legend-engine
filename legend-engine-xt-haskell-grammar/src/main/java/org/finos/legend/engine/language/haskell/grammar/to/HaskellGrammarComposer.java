package org.finos.legend.engine.language.haskell.grammar.to;

import org.eclipse.collections.impl.utility.Iterate;
import org.finos.legend.engine.protocol.haskell.metamodel.*;

public class HaskellGrammarComposer {
    private static final String HASKELL_TYPE_COLON_CONVENTION = "::";
    private static final String HASKELL_CONS_COLON_CONVENTION = ":";

    //Variants of haskell flip these conventions, make them configurable
    private final String typeColonConvention;
    private final String consColonConvention;

    protected HaskellGrammarComposer(String typeColonConvention, String consColonConvention)
    {
        this.typeColonConvention = typeColonConvention;
        this.consColonConvention = consColonConvention;
    }

    public static HaskellGrammarComposer newInstance()
    {
        return new HaskellGrammarComposer(HASKELL_TYPE_COLON_CONVENTION, HASKELL_CONS_COLON_CONVENTION);
    }

    public String renderModule(HaskellModule module)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("module ").append(module.id).append("\n  where\n\n");

        for( TopLevelDeclaration dataType: module.elements)
        {
            if (dataType instanceof DataType)
            {
                renderTopLevelDecl(builder, (DataType) dataType);
            }
        }

        builder.append("\n");

        return builder.toString();
    }

    private void renderTopLevelDecl(StringBuilder builder, DataType dataType)
    {
        builder.append("data ").append(dataType.name);

        if(!Iterate.isEmpty(dataType.constructors ))
        {
            builder.append(" = ");
            boolean isFirst = true;
            for(NamedConstructor constructor: dataType.constructors)
            {
                if(!isFirst)
                    builder.append("\n | ");

                isFirst = false;
                renderNamedConstructor(builder, constructor);
            }
        }

        for(Deriving deriving: dataType.derivings)
        {
            builder.append("\n    deriving (");
            renderDeriving(builder, deriving);
            builder.append(")");
        }
    }

    protected void renderNamedConstructor(StringBuilder builder, NamedConstructor constructor) {
        builder.append(constructor.name);
        //TODO: ADD FOR DAML.append(" with");

        if (constructor instanceof RecordTypeConstructor)
        {
            renderRecordTypeConstructor(builder, (RecordTypeConstructor) constructor);
        }
    }

    protected void renderRecordTypeConstructor(StringBuilder builder, RecordTypeConstructor constructor)
    {
        builder.append(" { ");
        boolean isFirstField = true;
        for(Field field: constructor.fields)
        {
            if(!isFirstField)
                builder.append(", ");

            renderFieldConstructor(builder, field);
            isFirstField = false;
        }
        builder.append(" }");
    }

    protected void renderFieldConstructor(StringBuilder builder, Field field) {
        builder.append(field.name).append(" ").append(this.typeColonConvention).append(" ");
        renderType(builder, field.type);
    }

    private void renderType(StringBuilder builder, HaskellType type) {
        if (type instanceof NamedType)
        {
            NamedType namedType = (NamedType)type;
            builder.append(namedType.name);
        }
    }

    private void renderDeriving(StringBuilder builder, Deriving deriving) {
        builder.append(String.join(", ",deriving.deriving));
    }
}
