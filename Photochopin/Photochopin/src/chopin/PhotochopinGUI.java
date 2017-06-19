/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chopin;
import components.ImageFileView;
import components.ImageFilter;
import components.ImagePreview;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 *
 * @author Bruno Iochins Grisci
 */
public class PhotochopinGUI extends javax.swing.JFrame {

    /**
     * Creates new form PhotochopinGUI
     */
    
    BufferedImage imagem = null;
    BufferedImage imagemoriginal = null;
    BufferedImage imagemanterior = null;
    BufferedImage imagemtemp = null;
    BufferedImage imagemequalizada = null;
    BufferedImage histogramaperma = null;
    JFrame frame = new JFrame("Imagem original");
    JFrame frame2 = new JFrame("Edições");;
    JFrame frame4;
    JFrame frameHist = new JFrame("Histograma");
    JFrame framepermaHist = new JFrame("Histograma original");
    JFrame frameHistEq;
    Boolean first=false;
    Boolean firstHist = false;
    Boolean firstHistEq = false;
    Boolean firstEq = false;
    Boolean arbitro = true;
    
    double[][] kernel = new double[3][3];
   
    
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
}
    
    
    /////////////////////////////////////////////////////////////////////////
    
     public void abrir(String s) {
        
        try {
            imagem = ImageIO.read(new File(s));
            imagemanterior = deepCopy(imagem);
            imagemoriginal = deepCopy(imagem);
        }  catch (IOException e) {
            System.out.println("fail1");
        }
        exibirimagem(s);
            
     }
     
     ///////////////////////////////////////////////////////////////////////
     
        public void exibirimagem(String path){
         
         ImageIcon icon = new ImageIcon(path);
         JLabel label = new JLabel(); 
         label.setIcon(icon);
         frame.add(label);
         frame.pack();
         frame.setVisible(true); 
         exibirimagemeditada();
         first = true;
         
        Photos gray = new Photos();
        BufferedImage histograma = null;
        BufferedImage imgcinza = null;
        imgcinza = deepCopy(imagem);
        imgcinza = gray.cinzaficador(imgcinza);
        histograma = gray.histogramador(imgcinza);
        exibirpermahistograma(histograma);
     }
        
     public void exibirimagemeditada(){

         ImageIcon icon2 = new ImageIcon(imagem);
         JLabel label2 = new JLabel();
         if (frame.getX() + frame.getWidth() < 1500)
         {
            frame2.setLocation(frame.getX() + frame.getWidth(),frame.getY());
         }
         else{
            frame2.setLocation(1500,frame.getY());
         }
         label2.setIcon(icon2);
         frame2.add(label2);
         frame2.pack();
         frame2.setVisible(true);
         
        Photos gray = new Photos();
        BufferedImage histograma = null;
        BufferedImage imgcinza = null;
        imgcinza = deepCopy(imagem);
        imgcinza = gray.cinzaficador(imgcinza);
        if (firstHist){
            frameHist.getContentPane().removeAll();
        }
        histograma = gray.histogramador(imgcinza);
        exibirhistograma(histograma);
        firstHist = true;
     }
     
     public void exibirimagemequalizada(){

         frame4 = new JFrame("Imagem equalizada");
         ImageIcon icon4 = new ImageIcon(imagemequalizada);
         JLabel label4 = new JLabel();
         if (frame2.getX() + frame2.getWidth() < 1500)
         {
            frame4.setLocation(frame2.getX() + frame2.getWidth(),frame2.getY());
         }
         else{
            frame4.setLocation(1500,frame2.getY());
         }
         label4.setIcon(icon4);
         frame4.add(label4);
         frame4.pack();
         frame4.setVisible(true);
         
        Photos gray = new Photos();
        BufferedImage histograma = null;
        BufferedImage imgcinza = null;
        imgcinza = deepCopy(imagemequalizada);
        imgcinza = gray.cinzaficador(imgcinza);
        if (firstHistEq){
            frameHistEq.dispose();
        }
        histograma = gray.histogramador(imgcinza);
        exibirhistogramaeq(histograma);
        firstHistEq = true;
        firstEq = true;
     }
     
     public void exibirhistograma(BufferedImage imhist){
         
         ImageIcon icon3 = new ImageIcon (imhist);
         JLabel label3 = new JLabel();
         if (frame2.getY() + frame2.getHeight() < 800){
             frameHist.setLocation(frame2.getX(),frame2.getY() + frame2.getHeight());
         }
         else{
             frameHist.setLocation(frame2.getX(),800);
         }
         label3.setIcon(icon3);
         frameHist.add(label3);
         frameHist.pack();
         frameHist.setVisible(true);
     }
     
     public void exibirpermahistograma(BufferedImage imhist){
         
         histogramaperma = deepCopy(imhist);
         ImageIcon icon4 = new ImageIcon (imhist);
         JLabel label4 = new JLabel();
         if (frame.getY() + frame.getHeight() < 800){
             framepermaHist.setLocation(frame.getX(),frame.getY() + frame.getHeight());
         }
         else{
             frameHist.setLocation(frame.getX(),800);
         }
         label4.setIcon(icon4);
         framepermaHist.add(label4);
         framepermaHist.pack();
         framepermaHist.setVisible(true);
     }
     
     public void exibirhistogramaeq(BufferedImage imhist){
         
         frameHistEq = new JFrame("Histograma equalizado");
         ImageIcon icon6 = new ImageIcon (imhist);
         JLabel label6 = new JLabel();
         if (frame4.getY() + frame4.getHeight() < 800){
             frameHistEq.setLocation(frame4.getX(),frame4.getY() + frame4.getHeight());
         }
         else{
             frameHistEq.setLocation(frame4.getX(),800);
         }
         label6.setIcon(icon6);
         frameHistEq.add(label6);
         frameHistEq.pack();
         frameHistEq.setVisible(true);
     }

     
     ///////////////////////////////////////////////////////////////////////
     
     public void salvar (BufferedImage imagem) {
        
        String destino = getpath(false); 
        try {
            File outputfile = new File(destino);
            ImageIO.write(imagem, "jpg", outputfile);
        } catch (IOException e) {
            System.out.println("fail2");
        }       
     }
     
     ///////////////////////////////////////////////////
     
     public String getpath (boolean oque) {
         
       
       JFileChooser fc = new JFileChooser();
       int returnVal = 0;
       
       fc.addChoosableFileFilter(new ImageFilter());
       fc.setFileView(new ImageFileView());
       fc.setAcceptAllFileFilterUsed(false);
       fc.setAccessory(new ImagePreview(fc));
       if (oque)
       {

           returnVal = fc.showOpenDialog(this);
       }
       else
       { 
           returnVal = fc.showSaveDialog(this);
       }
       String coisa = fc.getSelectedFile().getPath(); 
       return coisa;
         
     }
    
    /////////////////////////////////////////////////////
     
    
    public PhotochopinGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        checkbox2 = new java.awt.Checkbox();
        checkbox4 = new java.awt.Checkbox();
        SQuantizar = new javax.swing.JSlider();
        BTonsdecinza = new javax.swing.JButton();
        BQuantizar = new javax.swing.JButton();
        CQuantizar = new javax.swing.JSpinner();
        SBrilho = new javax.swing.JSlider();
        CBrilho = new javax.swing.JSpinner();
        BBrilho = new javax.swing.JButton();
        SContraste = new javax.swing.JSlider();
        CContraste = new javax.swing.JSpinner();
        BContraste = new javax.swing.JButton();
        BNegativo = new javax.swing.JButton();
        BEqualizacao = new javax.swing.JButton();
        CHECKBOXequalizacao = new java.awt.Checkbox();
        jSpinnerSXZOOM = new javax.swing.JSpinner();
        jSpinnerSYZOOM = new javax.swing.JSpinner();
        LabelLargura = new javax.swing.JLabel();
        LabelAltura = new javax.swing.JLabel();
        BZoomOut = new javax.swing.JButton();
        BZoomIn = new javax.swing.JButton();
        Kernel = new java.awt.Panel();
        LabelKernel = new javax.swing.JLabel();
        jSK1 = new javax.swing.JSpinner();
        jSK2 = new javax.swing.JSpinner();
        jSK4 = new javax.swing.JSpinner();
        jSK5 = new javax.swing.JSpinner();
        jSK6 = new javax.swing.JSpinner();
        jSK7 = new javax.swing.JSpinner();
        jSK8 = new javax.swing.JSpinner();
        jSK9 = new javax.swing.JSpinner();
        jComboBox1 = new javax.swing.JComboBox();
        jSK3 = new javax.swing.JSpinner();
        checkbox3 = new java.awt.Checkbox();
        checkbox2embossing = new java.awt.Checkbox();
        BFiltrar = new javax.swing.JButton();
        checkbox1 = new java.awt.Checkbox();
        jMenuBarra = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuCarregar = new javax.swing.JMenuItem();
        jMenuItemSalvar = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();
        jMenuItemDesfazer = new javax.swing.JMenuItem();
        jMenuItemRecomecar = new javax.swing.JMenuItem();
        jMenuExibir = new javax.swing.JMenu();
        jMenuItemHistograma = new javax.swing.JMenuItem();
        jMenuEspelhar = new javax.swing.JMenu();
        jMenuItemEspelhamentoHorizontal = new javax.swing.JMenuItem();
        jMenuItemEspelhamentoVertical = new javax.swing.JMenuItem();
        jMenuRotacao = new javax.swing.JMenu();
        jMenuItemRotacaoHoraria = new javax.swing.JMenuItem();
        jMenuItemRotacaoAntiHoraria = new javax.swing.JMenuItem();

        checkbox2.setLabel("checkbox1");

        checkbox4.setLabel("checkbox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photochopin - Bruno Iochins Grisci 208151");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        setForeground(new java.awt.Color(240, 240, 240));
        setLocationByPlatform(true);
        setResizable(false);

        SQuantizar.setMaximum(256);
        SQuantizar.setMinimum(1);
        SQuantizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SQuantizar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SQuantizarStateChanged(evt);
            }
        });

        BTonsdecinza.setText("Tons de cinza");
        BTonsdecinza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTonsdecinzaActionPerformed(evt);
            }
        });

        BQuantizar.setText("Quantizar");
        BQuantizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BQuantizarActionPerformed(evt);
            }
        });

        CQuantizar.setModel(new javax.swing.SpinnerNumberModel(256, 1, 256, 1));
        CQuantizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, SQuantizar, org.jdesktop.beansbinding.ELProperty.create("${value}"), CQuantizar, org.jdesktop.beansbinding.BeanProperty.create("value"), "");
        bindingGroup.addBinding(binding);

        SBrilho.setMaximum(2550);
        SBrilho.setMinimum(-2550);
        SBrilho.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SBrilho.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SBrilhoStateChanged(evt);
            }
        });

        CBrilho.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(-255.0f), Float.valueOf(255.0f), Float.valueOf(0.1f)));
        CBrilho.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CBrilhoStateChanged(evt);
            }
        });

        BBrilho.setText("Brilho");
        BBrilho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBrilhoActionPerformed(evt);
            }
        });

        SContraste.setMaximum(2550);
        SContraste.setMinimum(1);
        SContraste.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SContraste.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SContrasteStateChanged(evt);
            }
        });

        CContraste.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(255.0f), Float.valueOf(0.1f)));
        CContraste.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CContrasteStateChanged(evt);
            }
        });

        BContraste.setText("Contraste");
        BContraste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BContrasteActionPerformed(evt);
            }
        });

        BNegativo.setText("Negativo");
        BNegativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BNegativoActionPerformed(evt);
            }
        });

        BEqualizacao.setText("Equalização");
        BEqualizacao.setBorder(null);
        BEqualizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEqualizacaoActionPerformed(evt);
            }
        });

        CHECKBOXequalizacao.setLabel("Copiar equalização");

        jSpinnerSXZOOM.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jSpinnerSYZOOM.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        LabelLargura.setText("Largura");

        LabelAltura.setText("Altura");

        BZoomOut.setText("Diminuir imagem");
        BZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BZoomOutActionPerformed(evt);
            }
        });

        BZoomIn.setText("Dobrar tamanho");
        BZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BZoomInActionPerformed(evt);
            }
        });

        Kernel.setBackground(new java.awt.Color(204, 204, 204));

        LabelKernel.setText("Kernel para filtro de convolução");

        jSK1.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.001d)));

        jSK2.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK4.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK5.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(1.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK6.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK7.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK8.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jSK9.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.0010000000474974513d)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filtro arbitrário", "Gaussiano", "Laplaciano", "Passa Altas Genérico", "Prewitt horizontal", "Prewitt vertical", "Sobel horizontal", "Sobel vertical" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jSK3.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(0.001000046730041504d)));

        checkbox3.setLabel("checkbox1");

        checkbox2embossing.setLabel("Embossing");

        BFiltrar.setText("Aplicar filtro");
        BFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BFiltrarActionPerformed(evt);
            }
        });

        checkbox1.setLabel("checkbox1");

        javax.swing.GroupLayout KernelLayout = new javax.swing.GroupLayout(Kernel);
        Kernel.setLayout(KernelLayout);
        KernelLayout.setHorizontalGroup(
            KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KernelLayout.createSequentialGroup()
                .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(KernelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(KernelLayout.createSequentialGroup()
                                    .addComponent(jSK2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jSK3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(KernelLayout.createSequentialGroup()
                                    .addComponent(jSK5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jSK6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(KernelLayout.createSequentialGroup()
                                .addComponent(jSK8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSK9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(checkbox2embossing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(KernelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelKernel)))
                .addContainerGap())
        );

        KernelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSK1, jSK2, jSK3, jSK4, jSK5, jSK6, jSK7, jSK8, jSK9});

        KernelLayout.setVerticalGroup(
            KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KernelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelKernel)
                .addGap(24, 24, 24)
                .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSK2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSK3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(KernelLayout.createSequentialGroup()
                        .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSK4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BFiltrar))
                        .addGap(18, 18, 18)
                        .addGroup(KernelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSK7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSK9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(checkbox2embossing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        KernelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jSK1, jSK2, jSK3, jSK4, jSK5, jSK6, jSK7, jSK8, jSK9});

        jMenuArquivo.setText("Arquivo");

        jMenuCarregar.setText("Carregar");
        jMenuCarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCarregarActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuCarregar);

        jMenuItemSalvar.setText("Salvar");
        jMenuItemSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalvarActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuItemSalvar);

        jMenuBarra.add(jMenuArquivo);

        jMenuEditar.setText("Editar");

        jMenuItemDesfazer.setText("Desfazer");
        jMenuItemDesfazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDesfazerActionPerformed(evt);
            }
        });
        jMenuEditar.add(jMenuItemDesfazer);

        jMenuItemRecomecar.setText("Recomeçar");
        jMenuItemRecomecar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRecomecarActionPerformed(evt);
            }
        });
        jMenuEditar.add(jMenuItemRecomecar);

        jMenuBarra.add(jMenuEditar);

        jMenuExibir.setText("Exibir");

        jMenuItemHistograma.setText("Histograma");
        jMenuItemHistograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHistogramaActionPerformed(evt);
            }
        });
        jMenuExibir.add(jMenuItemHistograma);

        jMenuBarra.add(jMenuExibir);

        jMenuEspelhar.setText("Espelhamento");

        jMenuItemEspelhamentoHorizontal.setText("Horizontal");
        jMenuItemEspelhamentoHorizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEspelhamentoHorizontalActionPerformed(evt);
            }
        });
        jMenuEspelhar.add(jMenuItemEspelhamentoHorizontal);

        jMenuItemEspelhamentoVertical.setText("Vertical");
        jMenuItemEspelhamentoVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEspelhamentoVerticalActionPerformed(evt);
            }
        });
        jMenuEspelhar.add(jMenuItemEspelhamentoVertical);

        jMenuBarra.add(jMenuEspelhar);

        jMenuRotacao.setText("Rotação");
        jMenuRotacao.setToolTipText("");

        jMenuItemRotacaoHoraria.setText("Horária");
        jMenuItemRotacaoHoraria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRotacaoHorariaActionPerformed(evt);
            }
        });
        jMenuRotacao.add(jMenuItemRotacaoHoraria);

        jMenuItemRotacaoAntiHoraria.setText("Anti-horária");
        jMenuItemRotacaoAntiHoraria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRotacaoAntiHorariaActionPerformed(evt);
            }
        });
        jMenuRotacao.add(jMenuItemRotacaoAntiHoraria);

        jMenuBarra.add(jMenuRotacao);

        setJMenuBar(jMenuBarra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSpinnerSXZOOM, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CQuantizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(CBrilho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(CContraste, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(jSpinnerSYZOOM))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SQuantizar, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SBrilho, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SContraste, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BQuantizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BBrilho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BContraste, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(BNegativo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BTonsdecinza, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BEqualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(CHECKBOXequalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LabelLargura)
                                    .addComponent(LabelAltura))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BZoomOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BZoomIn, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Kernel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BNegativo, BTonsdecinza});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BNegativo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BTonsdecinza, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BEqualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CHECKBOXequalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CQuantizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SQuantizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BQuantizar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CBrilho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SBrilho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BBrilho, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CContraste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SContraste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BContraste, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerSXZOOM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelLargura)
                    .addComponent(BZoomOut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jSpinnerSYZOOM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LabelAltura))
                    .addComponent(BZoomIn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(Kernel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BNegativo, BTonsdecinza});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {SBrilho, SContraste, SQuantizar});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BBrilho, BContraste, BQuantizar});

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTonsdecinzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTonsdecinzaActionPerformed
        // TODO add your handling code here:
        Photos gray = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = gray.cinzaficador(imagem);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
        
    }//GEN-LAST:event_BTonsdecinzaActionPerformed

    private void BQuantizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BQuantizarActionPerformed
        // TODO add your handling code here:
        Photos quant = new Photos();
        int n = SQuantizar.getValue();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        imagemanterior = deepCopy(imagem);
        imagem = quant.quantizador(imagem,n);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_BQuantizarActionPerformed

    private void BBrilhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBrilhoActionPerformed
        // TODO add your handling code here:
        Photos brl = new Photos();
        SContraste.setValue(10);
        SQuantizar.setValue(256);
        float brilho = (float)SBrilho.getValue()/10;
        imagemanterior = deepCopy(imagem);
        imagem = brl.brilhabrilhaestrelinha(imagem,brilho);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_BBrilhoActionPerformed

    private void BContrasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BContrasteActionPerformed
        // TODO add your handling code here:
        Photos ctr = new Photos();
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        float contraste = (float)SContraste.getValue()/10;
        imagemanterior = deepCopy(imagem);
        imagem = ctr.contrasta(imagem,contraste);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_BContrasteActionPerformed

    private void BNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BNegativoActionPerformed
        // TODO add your handling code here:
        Photos neg = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = neg.negador(imagem);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_BNegativoActionPerformed

    private void BEqualizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEqualizacaoActionPerformed
        // TODO add your handling code here:
        Photos eq = new Photos();
        BufferedImage imgcinza = null;
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imgcinza = deepCopy(imagem);
        imgcinza = eq.cinzaficador(imgcinza);
        imagemequalizada = deepCopy(imagem);
        imagemequalizada = eq.equalizador(imgcinza,imagem,imagemequalizada);
        if (CHECKBOXequalizacao.getState()){
                imagemanterior = deepCopy(imagem);
                imagem = deepCopy(imagemequalizada);
                frame2.getContentPane().removeAll();
                exibirimagemeditada();
        }
               
        if (firstEq){
            frame4.dispose();
            }
      
        exibirimagemequalizada();
    }//GEN-LAST:event_BEqualizacaoActionPerformed

    private void SQuantizarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SQuantizarStateChanged
        // TODO add your handling code here:
        Photos quant = new Photos();
        
        SContraste.setValue(10);
        SBrilho.setValue(0);
        
        if (first){
        int n = SQuantizar.getValue();
        imagemanterior = deepCopy(imagem);
        imagemtemp = deepCopy(imagem);
        imagem = quant.quantizador(imagem,n);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
        imagem = deepCopy(imagemtemp);
        }
        
    }//GEN-LAST:event_SQuantizarStateChanged

    private void CBrilhoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CBrilhoStateChanged
        // TODO add your handling code here:
        SContraste.setValue(10);
        SQuantizar.setValue(256);
        SBrilho.setValue((int)(10*((Number)CBrilho.getValue()).floatValue()));
        SBrilho.repaint();
    }//GEN-LAST:event_CBrilhoStateChanged

    private void SBrilhoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SBrilhoStateChanged
        // TODO add your handling code here:
        SContraste.setValue(10);
        SQuantizar.setValue(256);
        
        CBrilho.setValue((float)SBrilho.getValue()/10);
        CBrilho.repaint();
              
        Photos brl = new Photos();
        
        if(first){
        float brilho = (float)SBrilho.getValue()/10;
        imagemanterior = deepCopy(imagem);
        imagemtemp = deepCopy(imagem);
        imagem = brl.brilhabrilhaestrelinha(imagem,brilho);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
        imagem = deepCopy(imagemtemp);
        }
    }//GEN-LAST:event_SBrilhoStateChanged

    private void CContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CContrasteStateChanged
        // TODO add your handling code here:

        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        
        SContraste.setValue((int)(10*((Number)CContraste.getValue()).floatValue()));
        SContraste.repaint();
    }//GEN-LAST:event_CContrasteStateChanged

    private void SContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SContrasteStateChanged
        // TODO add your handling code here:
        
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        
        CContraste.setValue((float)SContraste.getValue()/10);
        CContraste.repaint();
        
        Photos ctr = new Photos();
        if (first){
        float contraste = (float)SContraste.getValue()/10;
        imagemanterior = deepCopy(imagem);
        imagemtemp = deepCopy(imagem);
        imagem = ctr.contrasta(imagem,contraste);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
        imagem = deepCopy(imagemtemp);
        }
    }//GEN-LAST:event_SContrasteStateChanged

    private void BHistogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BVertical1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BVertical1ActionPerformed

    private void jMenuCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCarregarActionPerformed
        // TODO add your handling code here:
          // TODO add your handling code here:
       //  private void JFileChooserActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        if (first)
        {
         frame.getContentPane().removeAll();
         frame2.getContentPane().removeAll();
         frame.dispose();
         frame2.dispose();
        }
        if (firstEq){
            frame4.dispose();
        }
        if (firstHist)
        {
            frameHist.getContentPane().removeAll();
            framepermaHist.getContentPane().removeAll();
            frameHist.dispose();
            framepermaHist.dispose();
        }
        if (firstHistEq){
            frameHistEq.getContentPane().removeAll();
            frameHistEq.dispose();
            firstHistEq = false;
        }
        String path = getpath(true);
        abrir (path);
    }//GEN-LAST:event_jMenuCarregarActionPerformed

    private void jMenuItemSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalvarActionPerformed
        // TODO add your handling code here:
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        if (first){
        salvar(imagem);
        }
    }//GEN-LAST:event_jMenuItemSalvarActionPerformed

    private void jMenuItemDesfazerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDesfazerActionPerformed
        // TODO add your handling code here:
        if (first){
            SContraste.setValue(10);
            SBrilho.setValue(0);
            SQuantizar.setValue(256);
            imagem = deepCopy(imagemanterior);
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemDesfazerActionPerformed

    private void jMenuItemRecomecarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRecomecarActionPerformed
        // TODO add your handling code here:
       if (first){
            SContraste.setValue(10);
            SBrilho.setValue(0);
            SQuantizar.setValue(256);
            imagemanterior = deepCopy(imagem);
            imagem = deepCopy(imagemoriginal);
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemRecomecarActionPerformed

    private void jMenuItemEspelhamentoHorizontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEspelhamentoHorizontalActionPerformed
        // TODO add your handling code here:
        Photos flip = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = flip.espelhamentoh(imagem);
        if (first){
             frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemEspelhamentoHorizontalActionPerformed

    private void jMenuItemEspelhamentoVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEspelhamentoVerticalActionPerformed
        // TODO add your handling code here:
        Photos flip = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = flip.espelhamentov(imagem);
        if (first){
            frame2.getContentPane().removeAll();
        }
        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemEspelhamentoVerticalActionPerformed

    private void jMenuItemHistogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHistogramaActionPerformed
        // TODO add your handling code here:
        Photos gray = new Photos();
        BufferedImage histograma = null;
        BufferedImage imgcinza = null;

        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        if (firstHist){
            frameHist.getContentPane().removeAll();
            framepermaHist.getContentPane().removeAll();
        }
        imgcinza = deepCopy(imagem);
        imgcinza = gray.cinzaficador(imgcinza);
        histograma = gray.histogramador(imgcinza);
        firstHist = true;
        exibirhistograma(histograma);
        exibirpermahistograma(histogramaperma);
    }//GEN-LAST:event_jMenuItemHistogramaActionPerformed

    private void jMenuItemRotacaoHorariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRotacaoHorariaActionPerformed
        // TODO add your handling code here:
        Photos rot = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = rot.rotacionadorH(imagem);
        if (first){
             frame2.getContentPane().removeAll();
        }

        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemRotacaoHorariaActionPerformed

    private void BZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BZoomOutActionPerformed
        // TODO add your handling code here:
        Photos zo = new Photos();
        int escalax = ((Number)jSpinnerSXZOOM.getValue()).intValue(); 
        int escalay = ((Number)jSpinnerSYZOOM.getValue()).intValue();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = zo.zoomout(imagem,escalax,escalay);
        if (first){
             frame2.getContentPane().removeAll();
        }

        exibirimagemeditada();
    }//GEN-LAST:event_BZoomOutActionPerformed

    private void BZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BZoomInActionPerformed
        // TODO add your handling code here:
        Photos zi = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = zi.dobratamanho(imagem);
        if (first){
             frame2.getContentPane().removeAll();
        }

        exibirimagemeditada();
    }//GEN-LAST:event_BZoomInActionPerformed

    private void jMenuItemRotacaoAntiHorariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRotacaoAntiHorariaActionPerformed
        // TODO add your handling code here:
        Photos rot = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        imagemanterior = deepCopy(imagem);
        imagem = rot.rotacionadorAH(imagem);
        if (first){
             frame2.getContentPane().removeAll();
        }

        exibirimagemeditada();
    }//GEN-LAST:event_jMenuItemRotacaoAntiHorariaActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        
        int arbitrario = 0, gaussiano = 1, laplaciano = 2, pagenerico = 3, pwth = 4, pwtv = 5, sobelh = 6, sobelv = 7;
        
        if (jComboBox1.getSelectedIndex() == arbitrario){         
            
            jSK1.setEnabled(true);
            jSK2.setEnabled(true);
            jSK3.setEnabled(true);
            jSK4.setEnabled(true);
            jSK5.setEnabled(true);
            jSK6.setEnabled(true);
            jSK7.setEnabled(true);
            jSK8.setEnabled(true);
            jSK9.setEnabled(true);
            
            jSK1.setValue(0.0);
            jSK2.setValue(0.0);
            jSK3.setValue(0.0);
            jSK4.setValue(0.0);
            jSK5.setValue(1.0);
            jSK6.setValue(0.0);
            jSK7.setValue(0.0);
            jSK8.setValue(0.0);
            jSK9.setValue(0.0);
             
            checkbox2embossing.setState(false);
            checkbox2embossing.enable();
            
            arbitro = true;
            
        }
        
        else{  if (jComboBox1.getSelectedIndex() == gaussiano){
            
            jSK1.setValue(0.0625);
            jSK2.setValue(0.125);
            jSK3.setValue(0.0625);
            jSK4.setValue(0.125);
            jSK5.setValue(0.25);
            jSK6.setValue(0.125);
            jSK7.setValue(0.0625);
            jSK8.setValue(0.125);
            jSK9.setValue(0.0625);
            
            kernel[0][0] = (float)0.0625;
            kernel[1][0] = (float)0.125;
            kernel[2][0] = (float)0.0625;
            kernel[0][1] = (float)0.125;
            kernel[1][1] = (float)0.25;
            kernel[2][1] = (float)0.125;
            kernel[0][2] = (float)0.0625;
            kernel[1][2] = (float)0.125;
            kernel[2][2] = (float)0.0625;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
  
            checkbox2embossing.setState(false);
            checkbox2embossing.disable();
            
            arbitro = false;
            
        }
         
        else{ if (jComboBox1.getSelectedIndex() == laplaciano){
            
            jSK1.setValue(0);
            jSK2.setValue(-1);
            jSK3.setValue(0);
            jSK4.setValue(-1);
            jSK5.setValue(4);
            jSK6.setValue(-1);
            jSK7.setValue(0);
            jSK8.setValue(-1);
            jSK9.setValue(0);
            
            kernel[0][0] = (float)0;
            kernel[1][0] = (float)-1;
            kernel[2][0] = (float)0;
            kernel[0][1] = (float)-1;
            kernel[1][1] = (float)4;
            kernel[2][1] = (float)-1;
            kernel[0][2] = (float)0;
            kernel[1][2] = (float)-1;
            kernel[2][2] = (float)0;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
                       
            checkbox2embossing.setState(false);
            checkbox2embossing.disable();
            
            arbitro = false;
        }
         
        else{  if (jComboBox1.getSelectedIndex() == pagenerico){
            
            jSK1.setValue(-1);
            jSK2.setValue(-1);
            jSK3.setValue(-1);
            jSK4.setValue(-1);
            jSK5.setValue(8);
            jSK6.setValue(-1);
            jSK7.setValue(-1);
            jSK8.setValue(-1);
            jSK9.setValue(-1);
            
            kernel[0][0] = (float)-1;
            kernel[1][0] = (float)-1;
            kernel[2][0] = (float)-1;
            kernel[0][1] = (float)-1;
            kernel[1][1] = (float)8;
            kernel[2][1] = (float)-1;
            kernel[0][2] = (float)-1;
            kernel[1][2] = (float)-1;
            kernel[2][2] = (float)-1;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
                     
            checkbox2embossing.setState(false);
            checkbox2embossing.disable();
            
            arbitro = false;
        }
         
        else{  if (jComboBox1.getSelectedIndex() == pwth){
            
            jSK1.setValue(-1);
            jSK2.setValue(0);
            jSK3.setValue(1);
            jSK4.setValue(-1);
            jSK5.setValue(0);
            jSK6.setValue(1);
            jSK7.setValue(-1);
            jSK8.setValue(0);
            jSK9.setValue(1);
            
            kernel[0][0] = (float)-1;
            kernel[1][0] = (float)0;
            kernel[2][0] = (float)1;
            kernel[0][1] = (float)-1;
            kernel[1][1] = (float)0;
            kernel[2][1] = (float)1;
            kernel[0][2] = (float)-1;
            kernel[1][2] = (float)0;
            kernel[2][2] = (float)1;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
            
            checkbox2embossing.setState(true);
            checkbox2embossing.disable();
            
            arbitro = false;
            
        }
         
        else{ if (jComboBox1.getSelectedIndex() == pwtv){
            
            jSK1.setValue(-1);
            jSK2.setValue(-1);
            jSK3.setValue(-1);
            jSK4.setValue(0);
            jSK5.setValue(0);
            jSK6.setValue(0);
            jSK7.setValue(1);
            jSK8.setValue(1);
            jSK9.setValue(1);
            
            kernel[0][0] = (float)-1;
            kernel[1][0] = (float)-1;
            kernel[2][0] = (float)-1;
            kernel[0][1] = (float)0;
            kernel[1][1] = (float)0;
            kernel[2][1] = (float)0;
            kernel[0][2] = (float)1;
            kernel[1][2] = (float)1;
            kernel[2][2] = (float)1;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
                       
            checkbox2embossing.setState(true);
            checkbox2embossing.disable();
            
            arbitro = false;
            
        }
        
        else{ if (jComboBox1.getSelectedIndex() == sobelh){
            
            jSK1.setValue(-1);
            jSK2.setValue(0);
            jSK3.setValue(1);
            jSK4.setValue(-2);
            jSK5.setValue(0);
            jSK6.setValue(2);
            jSK7.setValue(-1);
            jSK8.setValue(0);
            jSK9.setValue(1);
            
            kernel[0][0] = (float)-1;
            kernel[1][0] = (float)0;
            kernel[2][0] = (float)1;
            kernel[0][1] = (float)-2;
            kernel[1][1] = (float)0;
            kernel[2][1] = (float)2;
            kernel[0][2] = (float)-1;
            kernel[1][2] = (float)0;
            kernel[2][2] = (float)1;
            
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
            
            checkbox2embossing.setState(true);
            checkbox2embossing.disable();
            
            arbitro = false;
        }
         
        else{ if (jComboBox1.getSelectedIndex() == sobelv){
                  
            jSK1.setEnabled(false);
            jSK2.setEnabled(false);
            jSK3.setEnabled(false);
            jSK4.setEnabled(false);
            jSK5.setEnabled(false);
            jSK6.setEnabled(false);
            jSK7.setEnabled(false);
            jSK8.setEnabled(false);
            jSK9.setEnabled(false);
            
            jSK1.setValue(-1.0);
            jSK2.setValue(-2.0);
            jSK3.setValue(-1.0);
            jSK4.setValue(0);
            jSK5.setValue(0);
            jSK6.setValue(0);
            jSK7.setValue(1.0);
            jSK8.setValue(2.0);
            jSK9.setValue(1.0);
            
            kernel[0][0] = (float)-1;
            kernel[1][0] = (float)-2;
            kernel[2][0] = (float)-1;
            kernel[0][1] = (float)0;
            kernel[1][1] = (float)0;
            kernel[2][1] = (float)0;
            kernel[0][2] = (float) 1;
            kernel[1][2] = (float)2;
            kernel[2][2] = (float)1;
            
            checkbox2embossing.setState(true);
            checkbox2embossing.disable();
            
            arbitro = false;
        } } } } } } } }
         
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void BFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BFiltrarActionPerformed
        // TODO add your handling code here:
        Photos filt = new Photos();
        SContraste.setValue(10);
        SBrilho.setValue(0);
        SQuantizar.setValue(256);
        
        
        Boolean embossing = false;
        
        embossing = checkbox2embossing.getState();
       
        if (arbitro){
                          
            kernel[0][0] = ((Number)jSK1.getValue()).doubleValue();    
            kernel[1][0] = ((Number)jSK2.getValue()).doubleValue();    
            kernel[2][0] = ((Number)jSK3.getValue()).doubleValue();    
            kernel[0][1] = ((Number)jSK4.getValue()).doubleValue();    
            kernel[1][1] = ((Number)jSK5.getValue()).doubleValue();    
            kernel[2][1] = ((Number)jSK6.getValue()).doubleValue();    
            kernel[0][2] = ((Number)jSK7.getValue()).doubleValue();    
            kernel[1][2] = ((Number)jSK8.getValue()).doubleValue();    
            kernel[2][2] = ((Number)jSK9.getValue()).doubleValue();    
           
        }
          
        imagemanterior = deepCopy(imagem);
        imagem = filt.cinzaficador(imagem);
        imagem = filt.convolucionador(imagem,kernel,embossing);
        if (first){
             frame2.getContentPane().removeAll();
        }

        exibirimagemeditada();
    }//GEN-LAST:event_BFiltrarActionPerformed

    
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
            java.util.logging.Logger.getLogger(PhotochopinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhotochopinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhotochopinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhotochopinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhotochopinGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BBrilho;
    private javax.swing.JButton BContraste;
    private javax.swing.JButton BEqualizacao;
    private javax.swing.JButton BFiltrar;
    private javax.swing.JButton BNegativo;
    private javax.swing.JButton BQuantizar;
    private javax.swing.JButton BTonsdecinza;
    private javax.swing.JButton BZoomIn;
    private javax.swing.JButton BZoomOut;
    private javax.swing.JSpinner CBrilho;
    private javax.swing.JSpinner CContraste;
    private java.awt.Checkbox CHECKBOXequalizacao;
    private javax.swing.JSpinner CQuantizar;
    private java.awt.Panel Kernel;
    private javax.swing.JLabel LabelAltura;
    private javax.swing.JLabel LabelKernel;
    private javax.swing.JLabel LabelLargura;
    private javax.swing.JSlider SBrilho;
    private javax.swing.JSlider SContraste;
    private javax.swing.JSlider SQuantizar;
    private java.awt.Checkbox checkbox1;
    private java.awt.Checkbox checkbox2;
    private java.awt.Checkbox checkbox2embossing;
    private java.awt.Checkbox checkbox3;
    private java.awt.Checkbox checkbox4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuBar jMenuBarra;
    private javax.swing.JMenuItem jMenuCarregar;
    private javax.swing.JMenu jMenuEditar;
    private javax.swing.JMenu jMenuEspelhar;
    private javax.swing.JMenu jMenuExibir;
    private javax.swing.JMenuItem jMenuItemDesfazer;
    private javax.swing.JMenuItem jMenuItemEspelhamentoHorizontal;
    private javax.swing.JMenuItem jMenuItemEspelhamentoVertical;
    private javax.swing.JMenuItem jMenuItemHistograma;
    private javax.swing.JMenuItem jMenuItemRecomecar;
    private javax.swing.JMenuItem jMenuItemRotacaoAntiHoraria;
    private javax.swing.JMenuItem jMenuItemRotacaoHoraria;
    private javax.swing.JMenuItem jMenuItemSalvar;
    private javax.swing.JMenu jMenuRotacao;
    private javax.swing.JSpinner jSK1;
    private javax.swing.JSpinner jSK2;
    private javax.swing.JSpinner jSK3;
    private javax.swing.JSpinner jSK4;
    private javax.swing.JSpinner jSK5;
    private javax.swing.JSpinner jSK6;
    private javax.swing.JSpinner jSK7;
    private javax.swing.JSpinner jSK8;
    private javax.swing.JSpinner jSK9;
    private javax.swing.JSpinner jSpinnerSXZOOM;
    private javax.swing.JSpinner jSpinnerSYZOOM;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
