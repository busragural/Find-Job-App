/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package findjob;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author BusraGural
 */
public class AccountPageUI extends javax.swing.JFrame {
    Connection conn;
    User currentUser;
    Education education = new Education();
    Experience experience = new Experience();
    Certificate certificate = new Certificate();
    ArrayList<Education> educationList;
    ArrayList<Experience> experienceList;
    ArrayList<Certificate> certificateList;
    //ArrayList<Education> educationList;
    private boolean edit = false;
    private DefaultTableModel educationTableModel;
    private DefaultTableModel experienceTableModel;
    private DefaultTableModel certificateTableModel;
    private DefaultTableModel applicationTable;
    
    /**
     * Creates new form AccountPageUI
     */
    public AccountPageUI(Connection conn) {
        this.conn = conn;
        
        
        
        currentUser = LoginUI.currentUser;
        System.out.println("name2 " + currentUser.getUsername());
        initComponents();
        currentUser = currentUser.fetchUserDetails(conn, currentUser.getUsername());
        System.out.println("current userin idsi" + currentUser.getId());
        System.out.println("current userin adi : " + currentUser.getName());
        nameField.setText(currentUser.getName() + " " + currentUser.getSurname());
        /*if(!edit){
            Helpers.disableTextFields(jPanel1);     
            Helpers.disableTableFields(eduTable);
        }*/
     
        
        educationList = new ArrayList<>();
        educationTableModel = new DefaultTableModel(new Object[]{"Okul Adı", "Bölüm adı", "Not Ortalaması", "Başlangıç Tarihi", "Bitiş Tarihi"}, 0);
        eduTable.setModel(educationTableModel);
        
        educationList = education.getEducationList(conn, currentUser.getId());
        setEducationFields(educationList);
        
        experienceList = new ArrayList<>();
        experienceTableModel = (DefaultTableModel) expTable.getModel();
        experienceList = experience.getExperienceList(conn, currentUser.getId());
        setExperienceFields(experienceList);
        
        certificateList = new ArrayList<>();
        certificateTableModel = (DefaultTableModel) certTable.getModel();
        certificateList = certificate.getCertificateList(conn, currentUser.getId());
        setCertificateFields(certificateList);
        
        //appList = new ArrayList<>();
        
        
        
//        experience = experience.getExperienceDetails(conn, currentUser.getId(), experience);
//        setExperienceFields();
//        
//        certificate = certificate.getCertificateDetails(conn, currentUser.getId(), certificate);
//        setCertificateFields();
        
    }
    int getEduId(int rowId){
        System.out.println("testete" + educationList.get(rowId).getId());
            return educationList.get(rowId).getId();
            
    }
    
    int getExpId(int rowId){
        System.out.println("testete" + educationList.get(rowId).getId());
        return experienceList.get(rowId).getId();
    }
    
    int getCertId(int rowId){
        System.out.println("testete" + certificateList.get(rowId).getId());
        return certificateList.get(rowId).getId();
    }
    
    private void setEducationFields(ArrayList<Education> educationList){
       if (educationTableModel != null) {
            for (Education edu : educationList) {
                // Assume that the table has columns with appropriate names
                Object[] rowData = {                                             
                        edu.getSchoolName(),
                        edu.getDepartment(),
                        edu.getGrade(),
                        edu.getStartDate(),
                        edu.getFinishDate()
                };
                educationTableModel.addRow(rowData);
            }
       }       
    }
    

    
//    public void setExperienceFields(){
//        companyNameField.setText(experience.getCompanyName());
//        depNameField.setText(experience.getDepartment());
//        jobField.setText(experience.getJobName());
//        
//        LocalDate date = experience.getStartDate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDate = date.format(formatter);
//        startField.setText(formattedDate);
//        
//        date = experience.getFinishDate();
//        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        formattedDate = date.format(formatter);
//        finishField.setText(formattedDate);
//        
//       
//    }
    
//    public void setCertificateFields(){
//          
//        certifNameField.setText(certificate.getCertifName());
//        LocalDate date = certificate.getReceiptDate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDate = date.format(formatter);
//        cstartField.setText(formattedDate);
//        certCompanyField.setText(certificate.getCompanyName());
//        durationField.setText(String.valueOf(certificate.getDuration()));
//      
//    }
//    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        imagePanel = new javax.swing.JPanel();
        nameField = new javax.swing.JTextField();
        educationLabel = new javax.swing.JLabel();
        educationPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        eduTable = new javax.swing.JTable();
        uni_name_edu = new javax.swing.JTextField();
        dep_name_edu = new javax.swing.JTextField();
        gpa_edu = new javax.swing.JTextField();
        end_date_edu = new com.toedter.calendar.JDateChooser();
        start_date_edu = new com.toedter.calendar.JDateChooser();
        experinceLabel = new javax.swing.JLabel();
        experiencePanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        expTable = new javax.swing.JTable();
        comp_name_exp = new javax.swing.JTextField();
        dep_name_exp = new javax.swing.JTextField();
        job_name_exp = new javax.swing.JTextField();
        start_date_exp = new com.toedter.calendar.JDateChooser();
        end_date_exp = new com.toedter.calendar.JDateChooser();
        certifLabel = new javax.swing.JLabel();
        certifPanel = new javax.swing.JPanel();
        cerfTable = new javax.swing.JScrollPane();
        certTable = new javax.swing.JTable();
        cert_name = new javax.swing.JTextField();
        cert_comp = new javax.swing.JTextField();
        validity_per_cert = new javax.swing.JTextField();
        get_date_cert = new com.toedter.calendar.JDateChooser();
        appLabel = new javax.swing.JLabel();
        appPanel = new javax.swing.JPanel();
        cerfTable1 = new javax.swing.JScrollPane();
        appTable = new javax.swing.JTable();
        goAccountPanel = new javax.swing.JPanel();
        homeBttn = new javax.swing.JButton();
        eduEditButton = new javax.swing.JButton();
        eduAddButton = new javax.swing.JButton();
        expEditButton = new javax.swing.JButton();
        expAddButton = new javax.swing.JButton();
        certAddButton = new javax.swing.JButton();
        certEditButton = new javax.swing.JButton();
        error_text = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(234, 231, 231));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 500));

        jPanel4.setBackground(new java.awt.Color(234, 231, 231));

        imagePanel.setBackground(new java.awt.Color(118, 179, 157));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        nameField.setBackground(new java.awt.Color(231, 231, 231));
        nameField.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        nameField.setForeground(new java.awt.Color(40, 55, 57));
        nameField.setText("AD SOYAD");
        nameField.setCaretColor(new java.awt.Color(3, 39, 103));
        nameField.setDisabledTextColor(new java.awt.Color(40, 55, 57));
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        educationLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        educationLabel.setForeground(new java.awt.Color(40, 55, 57));
        educationLabel.setText("Eğitim");

        educationPanel.setBackground(new java.awt.Color(231, 231, 231));
        educationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        eduTable.setBackground(new java.awt.Color(234, 231, 231));
        eduTable.setForeground(new java.awt.Color(40, 55, 57));
        eduTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Üniversite Adı", "Bölüm Adı", "Not Ortalaması", "Başlangıç Tarihi", "Bitiş Tarihi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eduTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eduTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(eduTable);

        uni_name_edu.setText("Üniversite Adı");
        uni_name_edu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uni_name_eduActionPerformed(evt);
            }
        });

        dep_name_edu.setText("Bölüm Adı");
        dep_name_edu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dep_name_eduActionPerformed(evt);
            }
        });

        gpa_edu.setText("Not Ortalaması");

        end_date_edu.setDateFormatString("yyyy-MM-dd");

        start_date_edu.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout educationPanelLayout = new javax.swing.GroupLayout(educationPanel);
        educationPanel.setLayout(educationPanelLayout);
        educationPanelLayout.setHorizontalGroup(
            educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, educationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uni_name_edu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dep_name_edu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gpa_edu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(start_date_edu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(end_date_edu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addComponent(jScrollPane2)
        );
        educationPanelLayout.setVerticalGroup(
            educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(educationPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, educationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(uni_name_edu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dep_name_edu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(gpa_edu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(end_date_edu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(start_date_edu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        experinceLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        experinceLabel.setForeground(new java.awt.Color(40, 55, 57));
        experinceLabel.setText("Deneyim");

        experiencePanel.setBackground(new java.awt.Color(231, 231, 231));
        experiencePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        expTable.setBackground(new java.awt.Color(234, 231, 231));
        expTable.setForeground(new java.awt.Color(40, 55, 57));
        expTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kurum Adı", "Departman", "Meslek Adı", "Başlangıç Tarihi", "Bitiş Tarihi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        expTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(expTable);

        comp_name_exp.setText("Kurum Adı");
        comp_name_exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comp_name_expActionPerformed(evt);
            }
        });

        dep_name_exp.setText("Departman");

        job_name_exp.setText("Meslek Adı");

        start_date_exp.setDateFormatString("yyyy-MM-dd");

        end_date_exp.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout experiencePanelLayout = new javax.swing.GroupLayout(experiencePanel);
        experiencePanel.setLayout(experiencePanelLayout);
        experiencePanelLayout.setHorizontalGroup(
            experiencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(experiencePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comp_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dep_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(job_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(start_date_exp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(end_date_exp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        experiencePanelLayout.setVerticalGroup(
            experiencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(experiencePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(experiencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, experiencePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comp_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dep_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(job_name_exp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(start_date_exp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(end_date_exp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        certifLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        certifLabel.setForeground(new java.awt.Color(40, 55, 57));
        certifLabel.setText("Sertifikalar");

        certifPanel.setBackground(new java.awt.Color(231, 231, 231));
        certifPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        certTable.setBackground(new java.awt.Color(234, 231, 231));
        certTable.setForeground(new java.awt.Color(40, 55, 57));
        certTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sertifika Adı", "Kurum", "Alınış Tarihi", "Geçerlilik Süresi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        certTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                certTableMouseClicked(evt);
            }
        });
        cerfTable.setViewportView(certTable);

        cert_name.setText("Sertifika Adı");
        cert_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cert_nameActionPerformed(evt);
            }
        });

        cert_comp.setText("Kurum Adı");
        cert_comp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cert_compActionPerformed(evt);
            }
        });

        validity_per_cert.setText("Geçerlilik Süresi");

        get_date_cert.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout certifPanelLayout = new javax.swing.GroupLayout(certifPanel);
        certifPanel.setLayout(certifPanelLayout);
        certifPanelLayout.setHorizontalGroup(
            certifPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cerfTable)
            .addGroup(certifPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cert_name, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cert_comp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(get_date_cert, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(validity_per_cert)
                .addContainerGap())
        );
        certifPanelLayout.setVerticalGroup(
            certifPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, certifPanelLayout.createSequentialGroup()
                .addComponent(cerfTable, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(certifPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(certifPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cert_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cert_comp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(validity_per_cert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(get_date_cert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        appLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        appLabel.setForeground(new java.awt.Color(40, 55, 57));
        appLabel.setText("Başvurularım");

        appPanel.setBackground(new java.awt.Color(231, 231, 231));
        appPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        appTable.setBackground(new java.awt.Color(234, 231, 231));
        appTable.setForeground(new java.awt.Color(40, 55, 57));
        appTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "İlan Başlığı", "Kurum", "Başvuru Tarihi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cerfTable1.setViewportView(appTable);

        javax.swing.GroupLayout appPanelLayout = new javax.swing.GroupLayout(appPanel);
        appPanel.setLayout(appPanelLayout);
        appPanelLayout.setHorizontalGroup(
            appPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cerfTable1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        appPanelLayout.setVerticalGroup(
            appPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cerfTable1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        );

        goAccountPanel.setBackground(new java.awt.Color(118, 179, 157));

        homeBttn.setBackground(new java.awt.Color(118, 179, 157));
        homeBttn.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        homeBttn.setText("◀");
        homeBttn.setAlignmentY(0.0F);
        homeBttn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        homeBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout goAccountPanelLayout = new javax.swing.GroupLayout(goAccountPanel);
        goAccountPanel.setLayout(goAccountPanelLayout);
        goAccountPanelLayout.setHorizontalGroup(
            goAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(goAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(homeBttn, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );
        goAccountPanelLayout.setVerticalGroup(
            goAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(goAccountPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(homeBttn, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );

        eduEditButton.setBackground(new java.awt.Color(231, 231, 231));
        eduEditButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        eduEditButton.setForeground(new java.awt.Color(118, 179, 157));
        eduEditButton.setText("Düzenle");
        eduEditButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eduEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eduEditButtonActionPerformed(evt);
            }
        });

        eduAddButton.setBackground(new java.awt.Color(231, 231, 231));
        eduAddButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        eduAddButton.setForeground(new java.awt.Color(118, 179, 157));
        eduAddButton.setText("Ekle");
        eduAddButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eduAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eduAddButtonActionPerformed(evt);
            }
        });

        expEditButton.setBackground(new java.awt.Color(231, 231, 231));
        expEditButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        expEditButton.setForeground(new java.awt.Color(118, 179, 157));
        expEditButton.setText("Düzenle");
        expEditButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expEditButtonActionPerformed(evt);
            }
        });

        expAddButton.setBackground(new java.awt.Color(231, 231, 231));
        expAddButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        expAddButton.setForeground(new java.awt.Color(118, 179, 157));
        expAddButton.setText("Ekle");
        expAddButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        expAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expAddButtonActionPerformed(evt);
            }
        });

        certAddButton.setBackground(new java.awt.Color(231, 231, 231));
        certAddButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        certAddButton.setForeground(new java.awt.Color(118, 179, 157));
        certAddButton.setText("Ekle");
        certAddButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        certAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                certAddButtonActionPerformed(evt);
            }
        });

        certEditButton.setBackground(new java.awt.Color(231, 231, 231));
        certEditButton.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        certEditButton.setForeground(new java.awt.Color(118, 179, 157));
        certEditButton.setText("Düzenle");
        certEditButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        certEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                certEditButtonActionPerformed(evt);
            }
        });

        error_text.setForeground(new java.awt.Color(255, 51, 0));
        error_text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        error_text.setText("jLabel1");
        error_text.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(goAccountPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(certifPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(experiencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(educationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(appLabel)
                            .addComponent(experinceLabel)
                            .addComponent(certifLabel)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(educationLabel)
                                .addGap(112, 112, 112)
                                .addComponent(error_text, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(eduAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                        .addGap(287, 287, 287))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(eduEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(expEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(certEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(expAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(certAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(goAccountPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(educationLabel)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(eduEditButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eduAddButton))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(educationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(experinceLabel)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(expEditButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(expAddButton)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(experiencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(certifLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(certifPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(certEditButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(certAddButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(appLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(appPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(error_text)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eduEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eduEditButtonActionPerformed
        // TODO add your handling code here:
        
        DefaultTableModel educationTable =(DefaultTableModel) eduTable.getModel();
        
        int secili_row = eduTable.getSelectedRow();
        
        if(secili_row == -1){
            if(eduTable.getRowCount() == 0){
                error_text.setText("Eğitim bilgisi şu anda boş!");
            }else{
                error_text.setText("Lutfen guncellenececk bir urun secin!");
            }
        }else{
     
            java.util.Date startDateUtil = start_date_edu.getDate();
            java.sql.Date startDateSql = new java.sql.Date(startDateUtil.getTime());

            java.util.Date endDateUtil = end_date_edu.getDate();
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());

            eduTable.setValueAt(uni_name_edu.getText(), secili_row, 0);
            eduTable.setValueAt(dep_name_edu.getText(), secili_row, 1);
            eduTable.setValueAt(gpa_edu.getText(), secili_row, 2);
            eduTable.setValueAt(startDateSql, secili_row, 3);
            eduTable.setValueAt(endDateSql, secili_row, 4);
            Education tmp;
            tmp = new Education(currentUser.getId(), uni_name_edu.getText(), dep_name_edu.getText(), Double.parseDouble(gpa_edu.getText()), startDateSql, endDateSql);
            
            education.updateEducationDetails(conn, currentUser.getId(), tmp, getEduId(secili_row));
        }
    }//GEN-LAST:event_eduEditButtonActionPerformed
    
    
    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void expEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expEditButtonActionPerformed

        DefaultTableModel experienceTable =(DefaultTableModel) expTable.getModel();
        
        int secili_row = expTable.getSelectedRow();
        
        if(secili_row == -1){
            if(expTable.getRowCount() == 0){
                error_text.setText("Deneyim tablosu şu anda boş!");
            }else{
                error_text.setText("Lutfen guncellenececk bir urun secin!");
            }
        }else{
     
            java.util.Date startDateUtil = start_date_exp.getDate();
            java.sql.Date startDateSql = new java.sql.Date(startDateUtil.getTime());

            java.util.Date endDateUtil = end_date_exp.getDate();
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());

            expTable.setValueAt(comp_name_exp.getText(), secili_row, 0);
            expTable.setValueAt(dep_name_exp.getText(), secili_row, 1);
            expTable.setValueAt(job_name_exp.getText(), secili_row, 2);
            expTable.setValueAt(startDateSql, secili_row, 3);
            expTable.setValueAt(endDateSql, secili_row, 4);
            Experience tmp;
            tmp = new Experience(currentUser.getId(), comp_name_exp.getText(), dep_name_exp.getText(), job_name_exp.getText(), startDateSql, endDateSql);
            
            experience.updateExperienceDetails(conn, currentUser.getId(), tmp, getExpId(secili_row));
        }
        
    }//GEN-LAST:event_expEditButtonActionPerformed

    private void certEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_certEditButtonActionPerformed
        
        DefaultTableModel certificateTable =(DefaultTableModel) certTable.getModel();
        
        int secili_row = certTable.getSelectedRow();
        
        if(secili_row == -1){
            if(eduTable.getRowCount() == 0){
                error_text.setText("Deneyim tablosu şu anda boş!");
            }else{
                error_text.setText("Lutfen guncellenececk bir urun secin!");
            }
        }else{
     
            java.util.Date startDateUtil = get_date_cert.getDate();
            java.sql.Date getDateSql = new java.sql.Date(startDateUtil.getTime());

            certTable.setValueAt(cert_name.getText(), secili_row, 0);
            certTable.setValueAt(cert_comp.getText(), secili_row, 1);
            certTable.setValueAt(getDateSql, secili_row, 2);
            certTable.setValueAt(validity_per_cert.getText(), secili_row, 3);
            
            int validityPerCert = Integer.parseInt(validity_per_cert.getText());
            
            Certificate tmp;
            tmp = new Certificate(currentUser.getId(), cert_name.getText(), cert_comp.getText(), validityPerCert, getDateSql);
            
            certificate.updateCertificateDetails(conn, currentUser.getId(), tmp, getCertId(secili_row));
        }
    }//GEN-LAST:event_certEditButtonActionPerformed

    private void eduAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eduAddButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel educationTable = (DefaultTableModel) eduTable.getModel();
        
        if(uni_name_edu.getText().trim().equals("")){
            error_text.setText("Üniversite ismi boş bırakılamaz!");
        }else{
            
            java.util.Date startDateUtil = start_date_edu.getDate();
            java.sql.Date startDateSql = new java.sql.Date(startDateUtil.getTime());

            java.util.Date endDateUtil = end_date_edu.getDate();
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());
            
            Object[] eklenecek = {uni_name_edu.getText(), dep_name_edu.getText(), gpa_edu.getText(), startDateSql, endDateSql};
            educationTable.addRow(eklenecek);
            
            Education tmp;
            tmp = new Education(currentUser.getId(), uni_name_edu.getText(), dep_name_edu.getText(), Double.parseDouble(gpa_edu.getText()), startDateSql, endDateSql);
            
            education.addEducation(conn, currentUser.getId(), tmp);
            
        }
        
       
    }//GEN-LAST:event_eduAddButtonActionPerformed

    private void expAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expAddButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel experienceTable = (DefaultTableModel) expTable.getModel();
        
        if(comp_name_exp.getText().trim().equals("")){
            error_text.setText("Kurum ismi boş bırakılamaz!");
        }else{
            
            java.util.Date startDateUtil = start_date_exp.getDate();
            java.sql.Date startDateSql = new java.sql.Date(startDateUtil.getTime());

            java.util.Date endDateUtil = end_date_exp.getDate();
            java.sql.Date endDateSql = new java.sql.Date(endDateUtil.getTime());
            
            Object[] eklenecek = {comp_name_exp.getText(), dep_name_exp.getText(), job_name_exp.getText(), startDateSql, endDateSql};
            experienceTable.addRow(eklenecek);
            
            Experience tmp;
            tmp = new Experience(currentUser.getId(), comp_name_exp.getText(), dep_name_exp.getText(), job_name_exp.getText(), startDateSql, endDateSql);
            
            experience.addExperience(conn, currentUser.getId(), tmp);
            
        }
    }//GEN-LAST:event_expAddButtonActionPerformed

    private void certAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_certAddButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel certificateTable = (DefaultTableModel) certTable.getModel();
        
        if(cert_name.getText().trim().equals("")){
            error_text.setText("Sertifika ismi boş bırakılamaz!");
        }else{
            
            java.util.Date startDateUtil = get_date_cert.getDate();
            java.sql.Date getDateSql = new java.sql.Date(startDateUtil.getTime());
            
            Object[] eklenecek = {cert_name.getText(), cert_comp.getText(), getDateSql, validity_per_cert.getText()};
            certificateTable.addRow(eklenecek);
            
            int validityPerCert = Integer.parseInt(validity_per_cert.getText());
            Certificate tmp;
            tmp = new Certificate(currentUser.getId(), cert_name.getText(), cert_comp.getText(), validityPerCert, getDateSql);
            
            certificate.addCertificate(conn, currentUser.getId(), tmp);
            
        }
        
    }//GEN-LAST:event_certAddButtonActionPerformed

    private void homeBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBttnActionPerformed
        // TODO add your handling code here:
        dispose();
        new AdvertisementUI(conn).setVisible(true);
    }//GEN-LAST:event_homeBttnActionPerformed

    private void dep_name_eduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dep_name_eduActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dep_name_eduActionPerformed

    private void uni_name_eduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uni_name_eduActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_uni_name_eduActionPerformed

    private void comp_name_expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comp_name_expActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comp_name_expActionPerformed

    private void cert_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cert_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cert_nameActionPerformed

    private void cert_compActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cert_compActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cert_compActionPerformed

    private void eduTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eduTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel educationTable = (DefaultTableModel) eduTable.getModel();
        
        int secili_row = eduTable.getSelectedRow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        uni_name_edu.setText(educationTable.getValueAt(secili_row, 0).toString());
        dep_name_edu.setText(educationTable.getValueAt(secili_row, 1).toString());
        gpa_edu.setText(educationTable.getValueAt(secili_row, 2).toString());
        try {
            start_date_edu.setDate(dateFormat.parse(educationTable.getValueAt(secili_row, 3).toString()));
        } catch (ParseException ex) {
            Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            end_date_edu.setDate(dateFormat.parse(educationTable.getValueAt(secili_row, 4).toString()));
            
            //dateFormat.parse(educationTable.getValueAt(secili_row, 3).toString);
        } catch (ParseException ex) {
            Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_eduTableMouseClicked

    private void expTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel experienceTable = (DefaultTableModel) expTable.getModel();
        
        int secili_row = expTable.getSelectedRow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        comp_name_exp.setText(experienceTable.getValueAt(secili_row, 0).toString());
        dep_name_exp.setText(experienceTable.getValueAt(secili_row, 1).toString());
        job_name_exp.setText(experienceTable.getValueAt(secili_row, 2).toString());
        try {
            start_date_exp.setDate(dateFormat.parse(experienceTable.getValueAt(secili_row, 3).toString()));
        } catch (ParseException ex) {
            Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            end_date_exp.setDate(dateFormat.parse(experienceTable.getValueAt(secili_row, 4).toString()));
            
            //dateFormat.parse(educationTable.getValueAt(secili_row, 3).toString);
        } catch (ParseException ex) {
            Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_expTableMouseClicked

    private void certTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_certTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel certificationTable = (DefaultTableModel) certTable.getModel();
        
        int secili_row = certTable.getSelectedRow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        cert_name.setText(certificationTable.getValueAt(secili_row, 0).toString());
        cert_comp.setText(certificationTable.getValueAt(secili_row, 1).toString());
        try {
            get_date_cert.setDate(dateFormat.parse(certificationTable.getValueAt(secili_row, 2).toString()));
        } catch (ParseException ex) {
            Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        validity_per_cert.setText(certificationTable.getValueAt(secili_row, 3).toString());
        
    }//GEN-LAST:event_certTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountPageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountPageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountPageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountPageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Connection conn = null;
               
                new AccountPageUI(conn).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appLabel;
    private javax.swing.JPanel appPanel;
    private javax.swing.JTable appTable;
    private javax.swing.JScrollPane cerfTable;
    private javax.swing.JScrollPane cerfTable1;
    private javax.swing.JButton certAddButton;
    private javax.swing.JButton certEditButton;
    private javax.swing.JTable certTable;
    private javax.swing.JTextField cert_comp;
    private javax.swing.JTextField cert_name;
    private javax.swing.JLabel certifLabel;
    private javax.swing.JPanel certifPanel;
    private javax.swing.JTextField comp_name_exp;
    private javax.swing.JTextField dep_name_edu;
    private javax.swing.JTextField dep_name_exp;
    private javax.swing.JButton eduAddButton;
    private javax.swing.JButton eduEditButton;
    private javax.swing.JTable eduTable;
    private javax.swing.JLabel educationLabel;
    private javax.swing.JPanel educationPanel;
    private com.toedter.calendar.JDateChooser end_date_edu;
    private com.toedter.calendar.JDateChooser end_date_exp;
    private javax.swing.JLabel error_text;
    private javax.swing.JButton expAddButton;
    private javax.swing.JButton expEditButton;
    private javax.swing.JTable expTable;
    private javax.swing.JPanel experiencePanel;
    private javax.swing.JLabel experinceLabel;
    private com.toedter.calendar.JDateChooser get_date_cert;
    private javax.swing.JPanel goAccountPanel;
    private javax.swing.JTextField gpa_edu;
    private javax.swing.JButton homeBttn;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField job_name_exp;
    private javax.swing.JTextField nameField;
    private com.toedter.calendar.JDateChooser start_date_edu;
    private com.toedter.calendar.JDateChooser start_date_exp;
    private javax.swing.JTextField uni_name_edu;
    private javax.swing.JTextField validity_per_cert;
    // End of variables declaration//GEN-END:variables

    private void setExperienceFields(ArrayList<Experience> experienceList) {
        if (experienceTableModel != null) {
            for (Experience exp : experienceList) {
                // Assume that the table has columns with appropriate names
                Object[] rowData = {                                             
                        exp.getCompanyName(),
                        exp.getDepartment(),
                        exp.getJobName(),
                        exp.getStartDate(),
                        exp.getFinishDate()
                };
                experienceTableModel.addRow(rowData);
            }
        }  
    }

    private void setCertificateFields(ArrayList<Certificate> certificateList) {
        if (certificateTableModel != null) {
            for (Certificate cert : certificateList) {
                // Assume that the table has columns with appropriate names
                Object[] rowData = {                                             
                        cert.getCertifName(),
                        cert.getCompanyName(),
                        cert.getReceiptDate(),
                        cert.getDuration()
                };
                certificateTableModel.addRow(rowData);
            }
        }  
    }
}
