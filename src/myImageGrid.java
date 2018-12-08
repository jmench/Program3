public class myImageGrid {
    private  ControlPoint[][] CPArray= new ControlPoint[20][20];
    private static Polygons[][] polygons = new Polygons[20][20];
    private static int gridSize;

    //Default constructor
    public void myImageGrid(){}

    public void setPolygons(Polygons[][] polygons){
        this.polygons = polygons;
    }

    public void setCPArray(ControlPoint[][] CPArray){
        this.CPArray = CPArray;
    }


    public ControlPoint[][] getCPArray() {
        return CPArray;
    }

    public void setGridSize(int gridSize){
        this.gridSize = gridSize;
    }

    public int getGridSize(){
        return gridSize;
    }
}
