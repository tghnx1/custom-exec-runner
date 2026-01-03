package com.github.tghnx1.customexecrunner.ui

import com.github.tghnx1.customexecrunner.runconfig.CustomExecRunConfiguration
import com.github.tghnx1.customexecrunner.runconfig.ExecMode
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.ButtonGroup
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JRadioButton

class CustomExecSettingsEditor : SettingsEditor<CustomExecRunConfiguration>() {

    private val rustcRadio = JRadioButton("Rust compiler in PATH (rustc)")
    private val cargoRadio = JRadioButton("Cargo in PATH (cargo)")
    private val customRadio = JRadioButton("Custom executable (choose file)")

    private val executableField = TextFieldWithBrowseButton()
    private val argsField = JBTextField()

    private val panel: JPanel

    init {
        ButtonGroup().apply {
            add(rustcRadio)
            add(cargoRadio)
            add(customRadio)
        }
        rustcRadio.isSelected = true

        val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
        // simple implementation for task:
        // deprecated API is acceptable here, as UI behavior is stable and simple
        executableField.addBrowseFolderListener(
            TextBrowseFolderListener(descriptor, null)
        )

        panel = FormBuilder.createFormBuilder()
            .addComponent(rustcRadio)
            .addComponent(cargoRadio)
            .addComponent(customRadio)
            .addLabeledComponent("Executable:", executableField)
            .addLabeledComponent("Arguments:", argsField)
            .panel

        updateEnabled()
        rustcRadio.addActionListener { updateEnabled() }
        cargoRadio.addActionListener { updateEnabled() }
        customRadio.addActionListener { updateEnabled() }
    }

    private fun updateEnabled() {
        executableField.isEnabled = customRadio.isSelected
    }

    override fun createEditor(): JComponent = panel

    override fun resetEditorFrom(s: CustomExecRunConfiguration) {
        when (s.mode) {
            ExecMode.RUSTC -> rustcRadio.isSelected = true
            ExecMode.CARGO -> cargoRadio.isSelected = true
            ExecMode.CUSTOM -> customRadio.isSelected = true
        }
        executableField.text = s.executablePath
        argsField.text = s.arguments
        updateEnabled()
    }

    override fun applyEditorTo(s: CustomExecRunConfiguration) {
        s.mode = when {
            rustcRadio.isSelected -> ExecMode.RUSTC
            cargoRadio.isSelected -> ExecMode.CARGO
            else -> ExecMode.CUSTOM
        }
        s.executablePath = executableField.text.trim()
        s.arguments = argsField.text
    }
}
