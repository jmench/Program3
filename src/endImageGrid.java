public class endImageGrid {
    private static ControlPoint[][] CPArray= new ControlPoint[20][20];
    private static Polygons[][] polygons = new Polygons[20][20];
    private static int gridSize;

    //Default constructor
    public void endImageGrid(){}

    public void setPolygons(Polygons[][] polygons){
        this.polygons = polygons;
    }

    public void setCPArray(ControlPoint[][] CPArray){
        this.CPArray = CPArray;

    }

    public Polygons[][] getPolygons(){
        return polygons;
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
