import evolutionRules.EvolutionRule;
import evolutionRules.lifeGameRules.B1S12Rule;
import evolutionRules.lifeGameRules.B36S23Rule;
import evolutionRules.lifeGameRules.B3S23D5Rule;
import evolutionRules.lifeGameRules.B3S23Rule;
import cell.Cell;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;


public class Main {

    public static void main(String[] args) throws IOException {
        // Read input parameters

        InputStream is = new FileInputStream(new File("src/main/resources/config.json"));
        JSONObject json = new JSONObject(new JSONTokener(is));

        int maxIterations = json.getInt("maxIterations");

        // Parse evolution rule

        String ruleStr = json.getString("evolutionRule");

        EvolutionRule rule;
        switch (ruleStr) {
            case "B1/S12":
                rule = new B1S12Rule(); break;
            case "B3/S23/D5":
                rule = new B3S23D5Rule(); break;
            case "B3/S23":
                rule = new B3S23Rule(); break;
            case "B36/S23":
                rule = new B36S23Rule(); break;
            default:
                throw new RuntimeException("Unknown rule name");
        }

        // Parse grid parameters

        JSONObject grid = (JSONObject) json.get("grid");

        String method = grid.getString("method");
        String type = grid.getString("type");
        int L = grid.getInt("size");

        Cell[][] grid2D = null;
        Cell[][][] grid3D = null;

        switch (method) {
            case "random":
                int p = grid.getJSONObject("random").getInt("aliveProportion");

                if (type.equals("2D"))
                    grid2D = randomGrid2D(L, p);
                else
                    grid3D = randomGrid3D(L, p);
                break;
            case "array": {
                JSONArray array = grid.getJSONArray("particles");

                if (type.equals("2D"))
                    grid2D = parsedGrid2D(L, array, true);
                break;
            }
            case "alive_coordinates": {
                JSONArray array = grid.getJSONArray("particles");

                if (type.equals("2D"))
                    grid2D = parsedGrid2D(L, array, false);
                else
                    grid3D = parsedGrid3D(L, array);
                break;
            }
            default:
                throw new RuntimeException("Invalid parameters");
        }

        if (grid2D != null)
            Automata.run(grid2D, rule, maxIterations);
        else if(grid3D != null)
            Automata.run(grid3D, rule, maxIterations);
    }

    public static Cell[][] parsedGrid2D(int L, JSONArray points, boolean isArray) {
        Cell[][] grid = new Cell[L][L];

        for(int i=0; i<points.length(); i++) {
            JSONArray point = points.getJSONArray(i);
            if (isArray) {
                for (int j = 0; j < point.length(); j++) {
                    grid[i][j] = new Cell(point.getInt(j) == 1);
                }
            } else {
                grid[point.getInt(0)][point.getInt(1)] = new Cell(true);
            }
        }

        for(int x=0; x<L; x++) {
            for(int y=0; y<L; y++) {
                if(grid[x][y] == null)
                    grid[x][y] = new Cell(false);
            }
        }

        return grid;
    }

    public static Cell[][][] parsedGrid3D(int L, JSONArray points) {
        Cell[][][] grid = new Cell[L][L][L];

        for(int i=0; i<points.length(); i++) {
            JSONArray point = points.getJSONArray(i);
            grid[point.getInt(0)][point.getInt(1)][point.getInt(2)] = new Cell(true);
        }

        for (int x=0; x<L; x++) {
            for (int y=0; y < L; y++) {
                for (int z=0; z < L; z++) {
                    if (grid[x][y][z] == null)
                        grid[x][y][z] = new Cell(false);
                }
            }
        }

        return grid;
    }

    private static Cell[][] randomGrid2D(int L, int p) {
        throw new RuntimeException();
    }

    private static Cell[][][] randomGrid3D(int L, int p) {
        throw new RuntimeException();
    }

}