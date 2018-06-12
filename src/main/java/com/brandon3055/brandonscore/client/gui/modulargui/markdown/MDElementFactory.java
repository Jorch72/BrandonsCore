package com.brandon3055.brandonscore.client.gui.modulargui.markdown;

import com.brandon3055.brandonscore.client.gui.modulargui.markdown.mdelements.*;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.reader.visitor.MarkdownVisitor;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.reader.visitor.property.*;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.visitorimpl.*;

import java.util.List;
import java.util.function.Supplier;

import static com.brandon3055.brandonscore.client.gui.modulargui.markdown.mdelements.MarkerElement.Type.NEW_LINE;

/**
 * Created by brandon3055 on 5/31/2018.
 */
public class MDElementFactory extends MarkdownVisitor {

    private MDElementContainer container;

    private boolean colourOverridden = false;
    private Supplier<Integer> colourSupplier = () -> 0x000000;
    private boolean shadow = false;
    private int colourOverride = 0;
    private int lineElements = 0;
    private boolean formatLine = false;

    /**
     * Will only allow a maximum of 1 blank line between elements
     */
    public boolean truncateNewLines = true;

    public MDElementFactory(MDElementContainer container) {
        this.container = container;
    }

    @Override
    public void startVisit() {

    }

    @Override
    public void startLine() {

    }

    //First pass      - Basic implementation but not fully tested.
    //Second pass     - All functions working including mouse interaction.
    //Second pass doc - Documentation has been updated and any required changes to insert code have been made.
    //Done            - Extensively tested and no issues found.

    //Second Pass doc
    @Override
    public StackVisitor visitStack(String stackString) {
        StackElement element = new StackElement(stackString);
        StackVisitor visitor = new StackVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    //Second Pass doc
    @Override
    public RecipeVisitor visitRecipe(String stackString) {
        RecipeElement element = new RecipeElement(stackString);
        RecipeVisitor visitor = new RecipeVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    //Second Pass doc
    @Override
    public ImageVisitor visitImage(String imageURL) {
        ImageElement element = new ImageElement(container, imageURL);
        ImageVisitor visitor = new ImageVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    //Second Pass doc TODO Link confirmation dialog
    @Override
    public LinkVisitor visitLink(String linkTarget) {
        LinkElement element = new LinkElement(container, linkTarget);
        LinkVisitor visitor = new LinkVisitorImpl(element);
        element.shadow = shadow;
        element.defaultColour = colourOverridden ? () -> colourOverride : colourSupplier;
        addElement(element);
        return visitor;
    }

    @Override
    public EntityVisitor visitEntity(String regName) {
        EntityElement element = new EntityElement(regName);
        EntityVisitor visitor = new EntityVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    //Second Pass doc
    @Override
    public RuleVisitor visitRule() {
        RuleElement element = new RuleElement();
        RuleVisitor visitor = new RuleVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    @Override
    public TableVisitor visitTable() {
        TableElement element = new TableElement();
        TableVisitor visitor = new TableVisitorImpl(element);
        addElement(element);
        return visitor;
    }

    @Override
    public void visitHeading(String text, int heading, boolean underlineDefinition) { //First Pass
        TextElement element = new TextElement(text, heading);
        element.colour = colourOverridden ? () -> colourOverride : colourSupplier;
        element.shadow = shadow;
        addElement(element);
    }

    @Override
    public void visitText(String text) { //First Pass
        TextElement element = new TextElement(text, 0);
        element.colour = colourOverridden ? () -> colourOverride : colourSupplier;
        element.shadow = shadow;
        addElement(element);
    }

    @Override
    public void visitAlignment(HAlign alignment) {
        addElement(MarkerElement.forAlignment(alignment));
    }

    @Override
    public void visitShadow(boolean enable) {
        shadow = enable;
        formatLine = true;
    }

    @Override
    public void visitColour(int argb) {
        colourOverride = argb;
        colourOverridden = true;
        formatLine = true;
    }

    @Override
    public void visitComment(String comment) {

    }

    @Override
    public void visitSkipped(String skipped) {

    }

    @Override
    public void endLine() {
        if (lineElements > 0) {
            colourOverridden = false;
            formatLine = false;
            lineElements = 0;
        }
        else if (formatLine) {
            return;
        }

        List<MDElementBase> eList = container.getElements();
        if (!truncateNewLines || eList.size() < 2 || !(MarkerElement.isNewLine(eList.get(eList.size() - 1)) && MarkerElement.isNewLine(eList.get(eList.size() - 2)))) {
            addElement(new MarkerElement(NEW_LINE));
        }
    }

    @Override
    public void endVisit() {

    }

    private void addElement(MDElementBase element) {
        if (!(element instanceof MarkerElement)) {
            lineElements++;
        }
        if (!element.getError().isEmpty()) {
            addErrorElement(element);
        }
        else {
            container.addElement(element);
        }
    }

    private void addErrorElement(MDElementBase erredElement) {
        TextElement error = new TextElement(erredElement.getError(), 0);
        error.colour = () -> 0xFF0000;
        error.shadow = true;
        addElement(error);
    }

    @Override
    public void visitError(String errorMessage) {
        TextElement error = new TextElement(errorMessage, 0);
        error.colour = () -> 0xFF0000;
        error.shadow = true;
        addElement(error);
    }

    public void setColourSupplier(Supplier<Integer> colourSupplier) {
        this.colourSupplier = colourSupplier;
    }
}