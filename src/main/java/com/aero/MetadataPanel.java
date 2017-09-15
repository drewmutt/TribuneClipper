package com.aero;

import com.aero.util.BoundsPopupMenuListener;
import com.aero.util.SpringUtilities;
import com.aero.util.WikiManager;
import org.wikipedia.Wiki;
import pixelitor.Composition;
import pixelitor.gui.ImageComponents;
import pixelitor.io.OutputFormat;

import javax.security.auth.login.FailedLoginException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MetadataPanel extends JPanel {


    private final CompositionMetadata _metadata;
    private final JTextArea _titleField;
    private final JTextArea _sourceField;
    private final JTextArea _dateField;
//    private final JTextField _sourceURLField;
    private final JTextField _authorField;
    private final JComboBox _licenseComboBox;
    private final JTextArea _descriptionField;

    private ArrayList<JTextComponent> _textComponents;

    public void updateMetaDataFromFields()
    {
        _metadata.setTitle(_titleField.getText());
        _metadata.setSource(_sourceField.getText());
        _metadata.setDate(_dateField.getText());
//        _metadata.setSource(_sourceField.getText());
        _metadata.setAuthor(_authorField.getText());
        _metadata.setDescription(_descriptionField.getText());
        ArrayList<ImageLicence> allLicenses = ImageLicence.getAllLicenses();
        ImageLicence imageLicence = allLicenses.get(_licenseComboBox.getSelectedIndex());
        _metadata.setImageLicence(imageLicence);
    }

    public void textChanged()
    {
        updateMetaDataFromFields();
    }

    public void listenToTextChanges()
    {
        for(JTextComponent text : _textComponents)
        {


            text.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
            text.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);


            text.getDocument().addDocumentListener(new DocumentListener()
            {
                public void changedUpdate(DocumentEvent e)
                {
                    textChanged();
                }

                public void removeUpdate(DocumentEvent e)
                {
                    textChanged();
                }

                public void insertUpdate(DocumentEvent e)
                {
                    textChanged();
                }
            });
        }
    }

    public void upload()
    {
        updateMetaDataFromFields();
        Wiki wiki = WikiManager.getWiki();
        try
        {
            final JFrame frame = new JFrame("Login Page");
            LoginDialog loginDlg = new LoginDialog(frame);
            loginDlg.setVisible(true);
            // if logon successfully
            if(loginDlg.isSucceeded()){
                    System.out.println("Yeah!");
            }
            wiki.login(loginDlg.getUsername(), loginDlg.getPassword());

            Wiki.User currentUser = wiki.getCurrentUser();
            Composition composition = ImageComponents.getActiveCompOrNull();
//                        File file = composition.getFile();
            OutputFormat outputFormat = OutputFormat.JPG;

            String markupForMetadata = _metadata.getMarkupForMetadata();


            File tempFile = File.createTempFile("temp_", ".JPG");
            outputFormat.saveComposition(composition, tempFile, false);
            String fileName = _metadata.getTitle();
            fileName = fileName.replace(".", " ");
            wiki.upload(tempFile, fileName, markupForMetadata, "");
            Map pageInfo = wiki.getPageInfo("File:" + fileName);

            JOptionPane.showMessageDialog(this, "Image uploaded");

            System.out.println(markupForMetadata);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateFieldFromMetadata()
    {
        if(_metadata.getSource() != null)
            _sourceField.setText(_metadata.getSource());

        if(_metadata.getDate() != null)
            _dateField.setText(_metadata.getDate());

//        if(_metadata.getSource() != null)
//            _sourceField.setText(_metadata.getSource());

        if(_metadata.getTitle() != null)
            _titleField.setText(_metadata.getTitle());

        if(_metadata.getDescription() != null)
            _descriptionField.setText(_metadata.getDescription());

        if(_metadata.getAuthor() != null)
            _authorField.setText(_metadata.getAuthor());

        if(_metadata.getImageLicence() != null)
        {
            ArrayList<ImageLicence> allLicenses = ImageLicence.getAllLicenses();
            for (int i = 0; i < allLicenses.size(); i++)
            {
                if(Objects.equals(allLicenses.get(i).getTemplateName(), _metadata.getImageLicence().getTemplateName()))
                {
                    _licenseComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }

    }

    public void addTextComponent(JTextComponent text)
    {
        _textComponents.add(text);
    }

    public MetadataPanel(CompositionMetadata compositionMetadata) {
        _textComponents = new ArrayList<>();

        _metadata = compositionMetadata;
        this.setLayout(new SpringLayout());


        JLabel titleLabel = new JLabel("Title", JLabel.TRAILING);
        this.add(titleLabel);
        JTextArea titleField =  new JTextArea(3, 10);
        titleLabel.setLabelFor(titleField);
        this.add(titleField);
        _titleField = titleField;
        addTextComponent(_titleField);

        //Create and populate the panel.
        JLabel l = new JLabel("Source", JLabel.TRAILING);
        this.add(l);
        JTextArea source =  new JTextArea(3, 10);
        l.setLabelFor(source);
        this.add(source);
        _sourceField = source;
        addTextComponent(_sourceField);

        JLabel dateLabel = new JLabel("Date", JLabel.TRAILING);
        this.add(dateLabel);
        JTextArea dateField = new JTextArea(3, 10);
        dateLabel.setLabelFor(dateField);
        this.add(dateField);
        _dateField = dateField;
        addTextComponent(_dateField);

        JLabel descriptionLabel = new JLabel("Description", JLabel.TRAILING);
        this.add(descriptionLabel);
        JTextArea descriptionField = new JTextArea(3, 10);
        descriptionLabel.setLabelFor(descriptionField);
        this.add(descriptionField);
        _descriptionField = descriptionField;
        addTextComponent(_descriptionField);

//        JLabel sourceURL = new JLabel("Source URL", JLabel.TRAILING);
//        this.add(sourceURL);
//        JTextField sourceURLField = new JTextField(10);
//        sourceURL.setLabelFor(sourceURLField);
//        this.add(sourceURLField);
//        _sourceURLField = sourceURLField;
//        addTextComponent(_sourceURLField);

        JLabel authorLabel = new JLabel("Author", JLabel.TRAILING);
        this.add(authorLabel);
        JTextField authorField = new JTextField(10);
        authorLabel.setLabelFor(authorField);
        this.add(authorField);
        _authorField = authorField;
        addTextComponent(_authorField);

        source.setLineWrap(true);
        dateField.setLineWrap(true);

        source.setPreferredSize(new Dimension(0, 0));


        ArrayList<ImageLicence> allLicenses = ImageLicence.getAllLicenses();
        String[] options = new String[allLicenses.size()];

        for(int i = 0; i < allLicenses.size(); i++)
        {
            ImageLicence licence = allLicenses.get(i);
            options[i] = licence.getTemplateName() + " - " + licence.getTemplateDescription();
        }
        JComboBox licenseComboBox = new JComboBox(options);
//        licenseComboBox.setSelectedIndex(4);

        JLabel licenseLabel = new JLabel("License", JLabel.TRAILING);
        this.add(licenseLabel);
        licenseLabel.setLabelFor(licenseComboBox);
        this.add(licenseComboBox);
        _licenseComboBox = licenseComboBox;

        BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);
        licenseComboBox.addPopupMenuListener( listener );
        licenseComboBox.setPrototypeDisplayValue("ItemWWW");

        licenseComboBox.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMetaDataFromFields();
            }
        });

        JLabel uploadLabel = new JLabel("Upload..", JLabel.TRAILING);
        this.add(uploadLabel);
        JButton button = new JButton("Upload");
        this.add(button);
        button.addActionListener(
                e ->
                {
                    try
                    {
                       upload();
                    } catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                });


        //Lay out the panel.
        int items = 7;
        SpringUtilities.makeCompactGrid(this,
                items, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //Create and set up the window.
        JFrame frame = new JFrame("SpringForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        this.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        updateFieldFromMetadata();
        listenToTextChanges();
    }



}
