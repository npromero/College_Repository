/*
Title: Creature
Names: Niklas Romero
Date; 11/6/2019
Class: CS1121 L01
Project description: The program creates instances of the creature class creating each creatures na, color,
texture, etc and change them. The creature can also eat and smile.
 */
public class Creature {
    private int teethNum = 10; // instance variables
    private  String color = "black";
    private String texture = "Scaly";
    private boolean antennae = false;
    private boolean carnivorous = true;
    private  int height = 420; //inches
    private String name = "Draco";
    public Creature() // default constructor
    {
        getTeethNum();
        getColor();
        getTexture();
        getAntennae();
        getCarnivorous();
        getHeight();
        getName();
    }
public  Creature(String name , String color, String texture)
{
    getTeethNum();
    setColor(color);
    setTexture(texture);
    getAntennae();
    getCarnivorous();
    getHeight();
    setName(name);
}
    public  Creature(int teethNum, String color, String texture,boolean antennae,boolean carnivorous,int height,String name)
    {
        setTeethNum(teethNum);
        setColor(color);
        setTexture(texture);
        setAntennae(antennae);
        setCarnivorous(carnivorous);
        setHeight(height);
        setName(name);
    }

    /*
    Getters below
     */
    public int getTeethNum() {return teethNum;}
    public String getColor() {return color;}
    public String getTexture() {return texture;}
    public boolean getAntennae(){return antennae;}
    public boolean getCarnivorous(){return carnivorous;}
    public int getHeight(){return height;}
    public String getName() { return name; }
    /*
    setters below
     */
    public void setTeethNum(int teethNum){ this.teethNum = teethNum;}
    public void setColor(String color){ this.color = color;}
    public void setTexture(String texture){ this.texture = texture;}
    public void setAntennae(boolean antennae){ this.antennae = antennae;}
    public void setCarnivorous(boolean carnivorous){ this.carnivorous = carnivorous;}
    public void setHeight(int height){ this.height = height;}
    public void setName(String name){ this.name = name;}

    /*
    To string below (printout statement)
     */
    public String toString()
    {
        String att = null;
        String carn = null;
        if (carnivorous)
        {
             carn = "carnivore";
        }
        else
        {
             carn = " herbivore";
        }
        if (antennae)
        {
             att = "antennaed";
        }
        else
        {
             att = " no antennaed";
        }

        String text = "Is a " + color + ", " + texture + ", " + att +" "+ height + " inch " + carn + " with " + teethNum + " teeth, which we have named " + name;
        return text;
    }
    /*
    Method Header: The method prints out a creature object eating their respective food
     */
    public void eat()
    {
        if (carnivorous)
        {
            System.out.println(name + " chews on some meat");
        }
        else
        {
            System.out.println(name + " nibbles on some leaves");
        }
    }
    /*
    Method Header: The method prints out the creature object smiling
     */
    public  void smile()
    {
        System.out.println(name + "Smiles with all " + teethNum + " of its teeth");
    }
}
