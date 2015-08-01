package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;



import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;


import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;



public class ModelXml {
    public enum wheelEnum {FL, FR, BL, BR}


    ;
    private Object object;
    private Set<UmlModelElement> elements;
    private Map<Integer, Persona> personas;
    private List<Map<String, Set>> collections;

    public Set<UmlModelElement> getElements() {
        return elements;
    }

    public void setElements(Set<UmlModelElement> elements) {
        this.elements = elements;
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void init() {
        object = new BigDecimal(3.14159265);
        Auto auto1 = new Auto();
        auto1.setModel("Focus");
        Character[] plate1 = new Character[6];
        plate1[0] = 'A';
        plate1[1] = 'B';
        plate1[2] = 'C';
        plate1[3] = '1';
        plate1[4] = '2';
        plate1[5] = '3';
        auto1.setPlate(plate1);
        auto1.setPrice(125000.5);
        auto1.setUsed(false);
        auto1.setWheels(new BigInteger("4"));
        Map<wheelEnum, Long> sizeofwhells1 = new TreeMap<>();
        sizeofwhells1.put(wheelEnum.FR, 23L);
        sizeofwhells1.put(wheelEnum.FL, 24L);
        sizeofwhells1.put(wheelEnum.BR, 25L);
        sizeofwhells1.put(wheelEnum.BL, 26L);
        auto1.setSizeOfWheel(sizeofwhells1);

        Auto auto2 = new Auto();
        auto2.setModel("Fitito");
        Character[] plate2 = new Character[6];
        plate2[0] = 'X';
        plate2[1] = 'Y';
        plate2[2] = 'Z';
        plate2[3] = '7';
        plate2[4] = '8';
        plate2[5] = '9';
        auto2.setPlate(plate2);
        auto2.setPrice(12.5);
        auto2.setUsed(true);
        auto2.setWheels(new BigInteger("12345678909876543210"));
        Map<wheelEnum, Long> sizeofwhells2 = new TreeMap<>();
        sizeofwhells2.put(wheelEnum.FR, 200L);
        sizeofwhells2.put(wheelEnum.FL, 2000L);
        sizeofwhells2.put(wheelEnum.BR, 20000L);
        sizeofwhells2.put(wheelEnum.BL, 200000L);
        auto2.setSizeOfWheel(sizeofwhells2);

        Persona carlos = new Persona();
        carlos.setCi(145.5F);
        carlos.setDni(33556650);
        carlos.setMoney(198512.00);
        carlos.setOwner(true);
        Set<Auto> autos = new HashSet<>();
        autos.add(auto1);
        autos.add(auto2);
        carlos.setAutos(autos);
        carlos.setTimeOfLife(333666666L);
        Hobbie guitar = new Hobbie();
        guitar.setName("tocar la guitarra");
        Hobbie correr = new Hobbie();
        correr.setName("correr 10km");
        List<Object> hobbiesDeCarlos = new LinkedList<>();
        hobbiesDeCarlos.add(guitar);
        hobbiesDeCarlos.add(correr);
        carlos.setHobbies(hobbiesDeCarlos);

        Persona carla = new Persona();
        carla.setCi(400F);
        carla.setDni(111222);
        carla.setMoney(2.00);
        carla.setOwner(true);
        carla.setAutos(null);
        carla.setTimeOfLife(7777777L);
        Hobbie planchar = new Hobbie();
        planchar.setName("planchar");
        Hobbie cocinar = new Hobbie();
        planchar.setName("cocinar");
        List<Object> hobbiesDeCarla = new ArrayList<>();
        hobbiesDeCarla.add(planchar);
        hobbiesDeCarla.add(cocinar);
        carla.setHobbies(hobbiesDeCarla);

        carlos.setCouple(carla);
        carla.setCouple(carlos);

        personas = new HashMap<>();
        personas.put(1, carlos);
        personas.put(2, carla);
        Set<String> cosas = new HashSet<>();
        cosas.add("lapicera");
        cosas.add("lapiz");
        Set<String> profesiones = new HashSet<>();
        profesiones.add("cantante");
        profesiones.add("ingeniero");

        Map<String, Set> mapaDeCosasyProf = new LinkedHashMap<>();
        mapaDeCosasyProf.put("cositas", cosas);
        mapaDeCosasyProf.put("laburos", profesiones);

        Set frutas = new TreeSet<>();
        frutas.add("banana");
        frutas.add("manzana");
        Set verduras = new TreeSet<>();
        verduras.add("apio");
        verduras.add("lechuga");

        Map<String, Set> mapaDeFrutasyVerduras = new HashMap<>();
        mapaDeFrutasyVerduras.put("fruits", frutas);
        mapaDeFrutasyVerduras.put("vegetables", verduras);

        collections = new ArrayList<>();
        collections.add(mapaDeCosasyProf);
        collections.add(mapaDeFrutasyVerduras);



    }

    public class Vehicle {
        public BigInteger wheels;
        public Map<wheelEnum, Long> sizeOfWheel;

        public BigInteger getWheels() {
            return wheels;
        }

        public void setWheels(BigInteger wheels) {
            this.wheels = wheels;
        }

        public Map<wheelEnum, Long> getSizeOfWheel() {
            return sizeOfWheel;
        }

        public void setSizeOfWheel(Map<wheelEnum, Long> sizeOfWheel) {
            this.sizeOfWheel = sizeOfWheel;
        }
    }


    public class Auto extends Vehicle {
        private String model;
        private Double price;
        private Boolean used;
        private Character[] plate;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Boolean getUsed() {
            return used;
        }

        public void setUsed(Boolean used) {
            this.used = used;
        }

        public Character[] getPlate() {
            return plate;
        }

        public void setPlate(Character[] plate) {
            this.plate = plate;
        }
    }


    public class Persona {
        private int dni;
        private boolean owner;
        private double money;
        private float ci;
        private long timeOfLife;
        private Set<Auto> autos;
        private Persona couple;
        private List<Object> hobbies;

        public int getDni() {
            return dni;
        }

        public void setDni(int dni) {
            this.dni = dni;
        }

        public boolean isOwner() {
            return owner;
        }

        public void setOwner(boolean owner) {
            this.owner = owner;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public float getCi() {
            return ci;
        }

        public void setCi(float ci) {
            this.ci = ci;
        }

        public long getTimeOfLife() {
            return timeOfLife;
        }

        public void setTimeOfLife(long timeOfLife) {
            this.timeOfLife = timeOfLife;
        }

        public Set<Auto> getAutos() {
            return autos;
        }

        public void setAutos(Set<Auto> autos) {
            this.autos = autos;
        }

        public Persona getCouple() {
            return couple;
        }

        public void setCouple(Persona couple) {
            this.couple = couple;
        }

        public List<Object> getHobbies() {
            return hobbies;
        }

        public void setHobbies(List<Object> hobbies) {
            this.hobbies = hobbies;
        }
    }


    public class Hobbie {
        private String name;

        public void setName(String name) {
            this.name = name;
        }
    }
}
