/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chopin;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Bruno Iochins Grisci
 */
public class Photos {
    
    public BufferedImage espelhamentoh(BufferedImage imagem) {
    //espelhamento horizontal
        
        AffineTransform tx = AffineTransform.getScaleInstance(-1,1);
        tx.translate(-imagem.getWidth(null),0);
        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        imagem = op.filter(imagem,null);
        
        return imagem;
}
    
    public BufferedImage espelhamentov(BufferedImage imagem) {
    //espelhamento vertical
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -imagem.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        imagem = op.filter(imagem, null);
    
        return imagem;
        
    }
    
    public BufferedImage cinzaficador(BufferedImage imagem){
    //Transforma a imagem em tons de cinza
        
	for(int w = 0; w < imagem.getWidth() ; w++)
	{
		for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
			Color color = new Color(imagem.getRGB(w, h));
				
			int luminosity = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
			Color lum = new Color(luminosity, luminosity, luminosity);
				
			imagem.setRGB(w, h, lum.getRGB());
                }
        }
        
        return imagem;
    }
    
    public BufferedImage quantizador(BufferedImage imagem, int quantlevel){
    //Quantiza a imagem em tons de cinza
        
        double n = 256/(quantlevel);
        double luminancia;
        double newv;
        
        for(int w=0; w<imagem.getWidth(); w++) {
            for(int h=0; h<imagem.getHeight(); h++) {
                Color c = new Color(imagem.getRGB(w,h));
                luminancia = 0.299*(c.getRed()) + 0.587*(c.getGreen()) + 0.114*(c.getBlue());
                
                newv = n*(Math.floor(luminancia/n));

                Color cq = new Color((int)newv, (int)newv, (int)newv, 0);               
                imagem.setRGB(w,h,cq.getRGB());
            }    
        }    
        return (imagem);
        
    }
    
    public BufferedImage negador(BufferedImage imagem){
    //Devolve o negativo da imagem fornecida
        
        for(int w = 0; w < imagem.getWidth() ; w++)
	{
		for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
			Color color = new Color(imagem.getRGB(w, h));
				
			int vermelho = (int)(255 - color.getRed());
                        int verde = (int)(255 - color.getGreen());
                        int azul = (int)(255 - color.getBlue());
			Color lum = new Color(vermelho, verde, azul);
				
			imagem.setRGB(w, h, lum.getRGB());
                }
        }
        
    return imagem;
    }
    
    public BufferedImage brilhabrilhaestrelinha (BufferedImage imagem, float brilho){
        
        for(int w = 0; w < imagem.getWidth() ; w++)
	{
		for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
			Color color = new Color(imagem.getRGB(w, h));
				
			int vermelho = (int)(brilho + color.getRed());
                        if (vermelho > 255){
                            vermelho = 255;}
                        if (vermelho < 0){
                            vermelho = 0;}
                        int verde = (int)(brilho + color.getGreen());
                        if (verde > 255){
                            verde = 255;}
                        if (verde < 0){
                            verde = 0;}
                        int azul = (int)(brilho + color.getBlue());
                        if (azul > 255){
                            azul = 255;}
                        if (azul < 0){
                            azul = 0;}
			Color lum = new Color(vermelho, verde, azul);
                        
				
			imagem.setRGB(w, h, lum.getRGB());
                }
        }
        
        return imagem;
    }
    
    public BufferedImage contrasta (BufferedImage imagem, float contraste){
        
        for(int w = 0; w < imagem.getWidth() ; w++)
	{
		for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
			Color color = new Color(imagem.getRGB(w, h));
				
			int vermelho = (int)(contraste*color.getRed());
                        if (vermelho > 255){
                            vermelho = 255;}
                        if (vermelho < 0){
                            vermelho = 0;}
                        int verde = (int)(contraste*color.getGreen());
                        if (verde > 255){
                            verde = 255;}
                        if (verde < 0){
                            verde = 0;}
                        int azul = (int)(contraste*color.getBlue());
                        if (azul > 255){
                            azul = 255;}
                        if (azul < 0){
                            azul = 0;}
			Color lum = new Color(vermelho, verde, azul);
                        
				
			imagem.setRGB(w, h, lum.getRGB());
                }
        }
        
        return imagem;
    }
    
    public BufferedImage histogramador (BufferedImage imagem){
        
        int[] hist;
        int totalpixels = imagem.getWidth()*imagem.getHeight();
        BufferedImage histograma = null;
        hist = new int[256];
        for(int i = 0; i < 256 ; i++)
	{
            hist[i]=0;
        }
        
        for(int w = 0; w < imagem.getWidth() ; w++)
	{
            for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
                    Color color = new Color(imagem.getRGB(w, h));
                    hist[color.getRed()] = hist[color.getRed()] + 1;
                }
        }
        
        histograma = criahistograma(histograma, hist, totalpixels);
        return (histograma);
    }
    
    public BufferedImage criahistograma(BufferedImage histograma, int[] hist, int totalpixels) {
        // Create a simple Bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int tom = 0; tom<256; tom++){
            dataset.setValue((double)hist[tom]/totalpixels, "Frequência", Integer.toString(tom));
        }
        JFreeChart chart = ChartFactory.createBarChart("Histograma da imagem em tons de cinza", "Tom", "Frequência", dataset, PlotOrientation.VERTICAL, false, true, false);
        histograma = chart.createBufferedImage(350,350);
        
        return (histograma);
        
    }
      
    ///////////////////////////////////////////////////////
    
    public BufferedImage equalizador(BufferedImage imagem, BufferedImage colorful, BufferedImage saida){
        
        int[] hist;
        float[] hist_cum;
        float totalpixels = imagem.getWidth()*imagem.getHeight();
        
        hist = new int[256];
        hist_cum = new float[256];
        float alfa = 255/totalpixels;
        
        for(int i = 0; i < 256 ; i++)
	{
            hist[i]=0;
        }
        
        for(int w = 0; w < imagem.getWidth() ; w++)
	{
            for(int h = 0 ; h < imagem.getHeight() ; h++)
		{
                    Color color = new Color(imagem.getRGB(w, h));
                    hist[color.getRed()] = hist[color.getRed()] + 1;
                }
        }
        
        hist_cum[0] = (alfa*hist[0]);
        for (int i=1; i < 256; i++){
            hist_cum[i] = hist_cum[i-1] + alfa*hist[i];
        }
        
        for(int w = 0; w < colorful.getWidth() ; w++)
	{
            for(int h = 0 ; h < colorful.getHeight() ; h++){
                Color color = new Color(colorful.getRGB(w, h));
                int vermelho = (int)hist_cum[color.getRed()];
                int verde = (int)hist_cum[color.getGreen()];
                int azul = (int)hist_cum[color.getBlue()];
                Color lum = new Color(vermelho, verde, azul);
                saida.setRGB(w, h, lum.getRGB());
            }
        }
                
        
        return (saida);
    }
    
    public BufferedImage rotacionadorH(BufferedImage imagem){
        
        int larguraoriginal = 0;
        int alturaoriginal = 0;
        int h2 = 0;
        int col = 1;
  
        larguraoriginal = imagem.getWidth();
        alturaoriginal = imagem.getHeight();
        
        BufferedImage imgrot = new BufferedImage (alturaoriginal,larguraoriginal,4);
        
        for(int w = 0; w < larguraoriginal ; w++){
            for(int h = 0 ; h < alturaoriginal ; h++){		
                 imgrot.setRGB(imagem.getHeight()-col, h2, imagem.getRGB(w,h));
                 col++;
            }
            col=1;
            h2++;
        }
        
        return (imgrot); 
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    
    public BufferedImage rotacionadorAH(BufferedImage imagem){
        
        int larguraoriginal = 0;
        int alturaoriginal = 0;
  
        larguraoriginal = imagem.getWidth();
        alturaoriginal = imagem.getHeight();
        
        BufferedImage imgrot = new BufferedImage (alturaoriginal,larguraoriginal,4);
        
        imgrot = rotacionadorH(imagem);
        imgrot = espelhamentoh(imgrot);
        imgrot = espelhamentov(imgrot);
        
        return (imgrot); 
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    
    public BufferedImage convolucionador (BufferedImage imagem, double[][] kernel, Boolean embossing){
            
        int largura = 0;
        int altura = 0; 
        
        largura = imagem.getWidth();
        altura = imagem.getHeight();
        
        BufferedImage imgfiltrada = new BufferedImage (largura,altura,4);
        
        int largK = 3;
        int altK = 3;
        
        double sum = 0;
        
        for (int w=1;w<(largura-1);w++){
            for (int h=1;h<(altura-1);h++){
               
                Color pixelA = new Color(imagem.getRGB(w-1, h-1));
                Color pixelB = new Color(imagem.getRGB(w, h-1));
                Color pixelC = new Color(imagem.getRGB(w+1, h-1));
                Color pixelD = new Color(imagem.getRGB(w-1, h));
                Color pixelE = new Color(imagem.getRGB(w, h));
                Color pixelF = new Color(imagem.getRGB(w+1, h));
                Color pixelG = new Color(imagem.getRGB(w-1, h+1));
                Color pixelH = new Color(imagem.getRGB(w, h+1));
                Color pixelI = new Color(imagem.getRGB(w+1, h+1));
                
                double a = kernel[0][0];
                double b = kernel[1][0];
                double c = kernel[2][0];
                double d = kernel[0][1];
                double e = kernel[1][1];
                double f = kernel[2][1];
                double g = kernel[0][2];
                double hh = kernel[1][2];
                double i = kernel[2][2];
                
                sum = (double)(i*pixelA.getGreen() + hh*pixelB.getGreen() + g*pixelC.getGreen() + f*pixelD.getGreen() + e*pixelE.getGreen() + d*pixelF.getGreen() + c*pixelG.getGreen() + b*pixelH.getGreen() + a*pixelI.getGreen());
                
                if (embossing){
                        sum = sum + 127;
                    }
                if (sum > 255){
                        sum = 255;
                    }
                if (sum < 0){
                        sum = 0;
                    }
                Color lum = new Color((int)sum, (int)sum,(int)sum,0);
                imgfiltrada.setRGB(w,h,lum.getRGB());
            }
        }
        
        return (imgfiltrada);
    }
    
    ////////////////////////////////////////////////////////////////////////
    
    public BufferedImage dobratamanho(BufferedImage imagem){
        
        int larguraoriginal = 0;
        int alturaoriginal = 0; 
        
        larguraoriginal = imagem.getWidth();
        alturaoriginal = imagem.getHeight();
        int novalargura= larguraoriginal+(larguraoriginal-1);
        int novaaltura= alturaoriginal+(alturaoriginal-1);        
        
        BufferedImage imgdobro = new BufferedImage (novalargura,novaaltura,4);
        
       for(int w = 0; w < larguraoriginal ; w++){
            for(int h = 0 ; h < alturaoriginal ; h++){		
                 imgdobro.setRGB(w*2, h*2, imagem.getRGB(w,h));
            }
        }
       
       for(int w = 1; w < novalargura ; w=w+2){
            for(int h = 0 ; h < novaaltura ; h=h+2){
                 Color pixelanterior = new Color(imgdobro.getRGB(w-1,h));
                 Color pixelseguinte = new Color(imgdobro.getRGB(w+1,h));
                 int vermelho = (int)((pixelanterior.getRed()+pixelseguinte.getRed())/2);
                 int verde = (int)((pixelanterior.getGreen()+pixelseguinte.getGreen())/2);
                 int azul = (int)((pixelanterior.getBlue()+pixelseguinte.getBlue())/2);
                 
                 Color lum = new Color(vermelho, verde, azul,0);
                 imgdobro.setRGB(w,h,lum.getRGB());
            }
        }
       
      for(int w = 0; w < novalargura ; w++){
            for(int h = 1 ; h < novaaltura ; h=h+2){
                 Color pixelanterior = new Color(imgdobro.getRGB(w,h-1));
                 Color pixelseguinte = new Color(imgdobro.getRGB(w,h+1));
                 int vermelho = (int)((pixelanterior.getRed()+pixelseguinte.getRed())/2);
                 int verde = (int)((pixelanterior.getGreen()+pixelseguinte.getGreen())/2);
                 int azul = (int)((pixelanterior.getBlue()+pixelseguinte.getBlue())/2);
                 
                 Color lum = new Color(vermelho, verde, azul,0);
                 imgdobro.setRGB(w,h,lum.getRGB());
            }
        }
       
       
        
        return (imgdobro);
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    
    public BufferedImage zoomout(BufferedImage imagem, int escalax, int escalay){        
        
        int larguraoriginal = 0;
        int alturaoriginal = 0; 
        
        larguraoriginal = imagem.getWidth();
        alturaoriginal = imagem.getHeight();
        
        int novalargura = (int)Math.ceil(larguraoriginal/escalax);
        int novaaltura = (int)Math.ceil(alturaoriginal/escalay);   
        
        BufferedImage imgredux = new BufferedImage (novalargura,novaaltura,4);
     
        for (int w=0, i=0; i<novalargura; w=w+escalax, i++){
            for (int h=0, j=0;j<novaaltura; h=h+escalay, j++){
                int vermelho = 0;
                int verde = 0;
                int azul = 0;
                int numerodepixels=0;
                for (int x=w;((x<w+escalax) && (x<larguraoriginal));x++){
                    for (int y=h;((y<h+escalay)&&(y<alturaoriginal));y++){
                         Color pixel = new Color (imagem.getRGB(x,y));
                         vermelho = vermelho + pixel.getRed();
                         verde = verde + pixel.getGreen();
                         azul = azul + pixel.getBlue();
                         numerodepixels++;
                    }
                }
                vermelho = (int)(vermelho/numerodepixels);
                verde = (int)(verde/numerodepixels);
                azul = (int)(azul/numerodepixels);
                if (vermelho > 255){ vermelho = 255;}
                if (vermelho < 0) {vermelho = 0;}
                if (verde > 255){ verde = 255;}
                if (verde < 0) {verde = 0;}
                if (azul > 255){ azul = 255;}
                if (azul < 0) {azul = 0;}
                Color lum = new Color(vermelho, verde, azul,0);
                imgredux.setRGB(i,j,lum.getRGB());
            }
        }
    
        return (imgredux);
    }
}



    
