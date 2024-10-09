package mg.fini_station.utils;
import java.lang.reflect.*;
import java.sql.*;
import java.io.*;
public class Fonctions {

    static public String getCatMethodName(String attributeName)
    {
        String get="get";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=get.concat(res);
        return methodName;
    }
    static public String setCatMethodName(String attributeName)
    {
        String set="set";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=set.concat(res);
        return methodName;
    }
    

    static public double somme(Object[] lsObjets , String attributeName) throws 
    IllegalAccessException,
    InvocationTargetException,
    IllegalArgumentException,
    NoSuchMethodException
    {
        Class toCast=null; 
        double res=0;
        if(attributeName==null)
        {
            for(int i=0 ; i<lsObjets.length ; i++)
            {
                if(lsObjets[i] instanceof Integer)
                {
                    res=res+(int)lsObjets[i];
                }
                else if(lsObjets[i] instanceof Double)
                {
                    res=res+(double)lsObjets[i];
                }          
            }
        }
        else
        {
            String methodName=getCatMethodName(attributeName);
            Class objClass=lsObjets[0].getClass();
            Method theOne=objClass.getMethod(methodName, (Class[])null);
            toCast=theOne.getDeclaringClass();

            for(int i=0 ; i<lsObjets.length ; i++)
            {
                if(theOne.invoke(toCast.cast(lsObjets[i]) , (Object[])null) instanceof Integer)
                {
                    res=res+(int)(theOne.invoke(toCast.cast(lsObjets[i]), (Object[])null));
                }
                else if(theOne.invoke(toCast.cast(lsObjets[i]) , (Object[])null) instanceof Double)
                {
                    res=res+(double)(theOne.invoke(toCast.cast(lsObjets[i]), (Object[])null));
                }
                
            }
        }
        return res;
    }

    public void triParMax(Object[] lsObjets , String attributeName)throws 
    IllegalAccessException,
    InvocationTargetException,
    IllegalArgumentException,
    NoSuchMethodException
    {
        String methodName=getCatMethodName(attributeName);
        Class objClass=lsObjets[0].getClass();
        Method theOne=objClass.getMethod(methodName, (Class[])null);
        int n = lsObjets.length; 
        for (int i = 0; i < n-1; i++) 
        {
            int max_idx = i;
            for (int j = i+1; j < n; j++)
            {
                if ((int)theOne.invoke(lsObjets[j],(Object[])null) > (int)theOne.invoke(lsObjets[max_idx],(Object[])null))
                    max_idx = j;
            }
            Object temp = lsObjets[max_idx];
            lsObjets[max_idx] = lsObjets[i];
            lsObjets[i] = temp;
        }
    }

    static public String removePackageName(String classNameWithPackage)
    {
        String res=null;
        String[] splitted=classNameWithPackage.split("\\.");
        res=splitted[splitted.length-1];
        return res;

    }


    static int isInThisArray(String toSearch , String[] array)
    {
        int res=0;
        for(int i=0 ; i<array.length ; i++)
        {
            if(toSearch.equals(array[i])==true)
            {
                res=1;
                break;
            }
        }
        return res;
    }

    static public void reOrderField(Field[] arrayField , String[][] arrayOrder)
    {
        for(int i=0 ; i<arrayField.length ; i++)
        {
            for(int j=0 ; j<arrayOrder.length ; j++)
            {
                if(arrayField[i].getName().equals(arrayOrder[j][0]))
                {
                    Field temp=arrayField[Integer.valueOf(arrayOrder[j][1])-1];
                    arrayField[Integer.valueOf(arrayOrder[j][1])-1]=arrayField[i];
                    arrayField[i]=temp;
                }
            }
        }
    }

    static public String getHTMLInput(Object objet , String[][] arrayOrder ,String[] notVisible , String[][][] selectForm, String[][] defaultValues) throws
    Exception
    
    {
        String debutHTML="<!DOCTYPE html>" +
        "<head>" +
        "<meta charset='UTF-8'>"+
        "<title>Formulaire</title>"+
        "</head>"+
        "<body>";

        Class laClasse=objet.getClass();
        Field[] lsAttributs=laClasse.getDeclaredFields();
        String classNameWithoutPackage=laClasse.getSimpleName();
        if(arrayOrder!=null)
        {
            reOrderField(lsAttributs, arrayOrder);
        }
        
        
        
        
        String classNameLow=classNameWithoutPackage.toLowerCase();
        String debutForm="<div class='panel panel-default'>"+
        "<div class='panel-body'>"+
        "<form class='form-group' action='" + classNameLow +".jsp' method='GET'>"+
        "<input type=hidden name='className' value='"+laClasse.getName()+"'";
        for(int i=0 ; i<lsAttributs.length ; i++)
        {
            String forme="input";
            String add="";
            if(selectForm!=null)
            {
                for(int j=0; j<selectForm.length ; j++)
                {
                    if(isInThisArray(lsAttributs[i].getName(),selectForm[j][0])==1)
                    {
                        forme="select";
                        String name=lsAttributs[i].getName();
                        String start="<p>"+name+"<select class='form-control' name='"+name+"'>";
                        String end="</select></p>";
                        for(int x=0 ; x<selectForm[j][1].length ; x++)
                        {
                            String value=selectForm[j][1][x];
                            String middle="<option value='"+value+"'>"+value+"</option>";
                            add=add+middle;
                        }
                        add=start+add+end;
                        debutForm=debutForm+add;
                    }
                }
            }
            if(forme.equals("input")==true)
            {
                Class typeField=lsAttributs[i].getType();
                String type="text";
                if(typeField.getName().equals("int") || typeField.getName().equals("double"))
                {   
                    type="number";
                }
                if(typeField.getName().equals("java.sql.Date"))
                {   
                    type="date";
                }
                String value="";
                String name=lsAttributs[i].getName();
                if(notVisible!=null && isInThisArray(lsAttributs[i].getName(), notVisible)==1)
                {
                    type="hidden";
                    name="";
                }
                if(defaultValues!=null)
                {
                    for(int j=0 ; j<defaultValues.length ; j++)
                    {
                        if(isInThisArray(lsAttributs[i].getName(), defaultValues[j])==1)
                        {
                            value=defaultValues[j][1];
                        }
                    } 
                }
                add="<p>"+name+" <input class='form-control' type='"+type+"' value='"+value+"' name='"+lsAttributs[i].getName()+"''>";
                debutForm=debutForm + add;
            }
        }
            
        String endForm="<p><input type='submit' class='btn btn-primary' value='Valider'></p>"+
        "</form>"+
        "</div>"+
        "</div>";/*+
        "</body>";*/

        String res=/*debutHTML + */debutForm + endForm ;
        return res;
    }

    static public String getHTMLObjectArray(Object[] objectArray , String attributeNameSum,String noneValue)
    throws Exception
    {
        if(objectArray==null){
            if(noneValue!=null){
                return"<h3>"+noneValue+"</h3>";            
            }
            return "<h3>Néant</h3>";
        }
        String debutHTML="<!DOCTYPE html>" +
        "<head>" +
        "<meta charset='UTF-8'>"+
        "<title>Tableau objet</title>"+
        "</head>"+
        "<body>";
        
        Class laClasse=objectArray[0].getClass();
        Field[] lsAttributs=laClasse.getDeclaredFields();
        String classNameWithoutPackage=laClasse.getSimpleName();
        
        
        
        String classNameLow=classNameWithoutPackage.toLowerCase();
        String debutTable="<table class='table table-striped table-responsive'>"+
        "<tr>";
        for(int i=0 ; i<lsAttributs.length ; i++)
        {
            String f="<th>";
            String e="</th>";
            String add=lsAttributs[i].getName().toUpperCase();
            add=f + add + e;
            debutTable=debutTable + add;

        }
        String endTableHead="</tr>";
        debutTable=debutTable + endTableHead;
        
        String milieu="";
        for(int i=0 ; i<objectArray.length ; i++)
        {
            String add="<tr>";
            
            for(int j=0 ; j<lsAttributs.length ; j++)
            {
                String first="<td>";
                String methodName=getCatMethodName(lsAttributs[j].getName());
                Method toUse=laClasse.getDeclaredMethod(methodName, (Class[])null);
                Class toCast=toUse.getDeclaringClass();
                /*String toPut=returnFunction.cast(toUse.invoke((toCast.cast(objectArray[0])), (Object[])null));*/
                first = first+toUse.invoke((toCast.cast(objectArray[i])), (Object[])null) + "</td>";
                add=add+first;
            }
            add=add+"</tr>";
            milieu=milieu+add;
        }

        if(attributeNameSum!=null)
        {
            String tdSomme="<td>Somme "+attributeNameSum+": "+somme(objectArray, attributeNameSum)+"</td>";
            milieu=milieu+tdSomme;
        }
        

        String endTableAndHtml="</table>"/* +
        "</body>" */;
        debutTable=debutTable+milieu+endTableAndHtml;
        System.out.println(debutTable);

        String res=debutTable;
        return res;
    }

    /*public JPanel getJPanelInput(String classNameWithPackage) throws ClassNotFoundException, ClassCastException
    {
        JPanel res=new JPanel();
        
        res.setPreferredSize(new Dimension(150,300));
        Class laClasse=Class.forName(classNameWithPackage);
        Field[] lsAttributs=laClasse.getDeclaredFields();
        String classNameWithoutPackage=this.removePackageName(classNameWithPackage);
        
        for(int i=0 ; i<lsAttributs.length ; i++)
        {
            JTextField toPut=new JTextField();
            JTextArea attributeName=new JTextArea(lsAttributs[i].getName());
            attributeName.setEditable(false);
            attributeName.setPreferredSize(new Dimension(120, 20));
            toPut.setPreferredSize(new Dimension(120,20));
            res.add(attributeName);
            res.add(toPut);
        }
        JButton boutonSubmit=new JButton("SET "+classNameWithoutPackage);
        ButtonClassListener listen= new ButtonClassListener(res ,classNameWithPackage,lsAttributs.length);
        boutonSubmit.setActionCommand("Submit");
        boutonSubmit.addActionListener(listen);
        res.add(boutonSubmit);
        return res;
    }*/

    /* public JPanel getJPanelInput(Object objet , String[][] arrayOrder , String[] notVisible , String[][][] selectForm ,String[][] defaultValues) throws Exception
    {
        JPanel res=new JPanel();
        res.setPreferredSize(new Dimension(150,100));
        res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
        Class laClasse=objet.getClass();
        Field[] lsAttributs=laClasse.getDeclaredFields();

        if(arrayOrder!=null)
        {
            this.reOrderField(lsAttributs, arrayOrder);
        }
        String classNameWithoutPackage=laClasse.getSimpleName();
        


        for(int i=0 ; i<lsAttributs.length ; i++)
        {
            String forme="input";
            String add="";
            if(selectForm!=null)
            {
                for(int j=0; j<selectForm.length ; j++)
                {
                    if(this.isInThisArray(lsAttributs[i].getName() , selectForm[j][0])==1)
                    {
                        forme="select";
                        String name=lsAttributs[i].getName();
                        JTextArea attributeName=new JTextArea(name);
                        attributeName.setEditable(false);
                        JComboBox select=new JComboBox(selectForm[j][1]);
                        res.add(attributeName);
                        res.add(select);
                    }
                }
            }

            if(forme.equals("input"))
            {
                boolean isVisible=true;
                String value="";
                if(notVisible!=null && this.isInThisArray(lsAttributs[i].getName(), notVisible)==1)
                {
                    isVisible=false;
                }
                if(defaultValues!=null)
                {
                    for(int j=0 ; j<defaultValues.length ; j++)
                    {
                        if(this.isInThisArray(lsAttributs[i].getName(), defaultValues[j])==1)
                        {
                            value=defaultValues[j][1];
                        }
                    } 
                }
                JTextField toPut=new JTextField();
                toPut.setVisible(isVisible);
                toPut.setText(value);
    
                JLabel attributeName=new JLabel(lsAttributs[i].getName());
                attributeName.setAlignmentX(attributeName.CENTER_ALIGNMENT);
                attributeName.setVisible(isVisible);
                attributeName.setMaximumSize(new Dimension(220, 40));
                toPut.setMaximumSize(new Dimension(220,40));
                res.add(attributeName);
                res.add(toPut);
                
            }
        }
        JButton boutonSubmit=new JButton("Save "+classNameWithoutPackage);
        ButtonClassListener listen= new ButtonClassListener(res ,laClasse.getName(),lsAttributs.length,objet);
        boutonSubmit.setAlignmentX(boutonSubmit.CENTER_ALIGNMENT);
        boutonSubmit.setActionCommand("Submit");
        boutonSubmit.addActionListener(listen);
        res.add(boutonSubmit);
        return res;
    }
 */


    static public void saveHTMLInput(String className , String[] valeurs) throws Exception
    {
        try
        {
            String fileName=removePackageName(className);
            fileName=fileName.toLowerCase();
            Class laClasse=Class.forName(className);
            Field[] lsAttributs=laClasse.getDeclaredFields();
            File file=new File("/home/fini/Documents/ProjetObjetSave/"+fileName+".txt");
            file.setWritable(true, false);
            file.setReadable(true, false);
            BufferedWriter toWrite=new BufferedWriter(new FileWriter(file, true));
            toWrite.write(className);
            toWrite.newLine();
            for(int i=0 ; i<valeurs.length ; i++)
            {
                toWrite.write(lsAttributs[i].getName());
                toWrite.write("=");
                toWrite.write(valeurs[i]);
                toWrite.newLine();
            }
            toWrite.write("/");
            toWrite.newLine();        
            toWrite.close();
        }
        catch(IOException ok)
        {

        }
    }
    public static void loadClass(Object objet , String[] data) throws Exception
    {
        try{
            Class laClasse=objet.getClass();
            Field[] lsAttributs=laClasse.getDeclaredFields();
            for(int i=0; i<lsAttributs.length ; i++)
            {
                String fieldName=lsAttributs[i].getName();
                Class type=lsAttributs[i].getType();
                Method setMethod=laClasse.getDeclaredMethod(Fonctions.setCatMethodName(fieldName), type);
                
                if(type.getName().equals("int"))
                {
                    int toset=Integer.valueOf(data[i]);
                    setMethod.invoke(objet, toset);
                }
                if(type.getName().equals("double"))
                {
                    double toset=Double.valueOf(data[i]);
                    setMethod.invoke(objet, toset);
                }
                if(type.getName().equals("java.sql.Date"))
                {
                    Date toset=Date.valueOf(data[i]);
                    setMethod.invoke(objet, toset);
                } 
            }

        }
        
        catch(InvocationTargetException e)
        {
            throw (Exception)e.getTargetException();
        }
    }
}


   


