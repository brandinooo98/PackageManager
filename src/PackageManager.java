import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    Brandon Erickson
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph graph;
    private Graph graphCopy;
    private ArrayList<String> dependencies;
    
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        graph = new Graph(); // Initializes graph
        graphCopy = new Graph();
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader(jsonFilepath)); // Used to parse file
        JSONObject parser = (JSONObject) obj;
        JSONArray packages = (JSONArray) parser.get("packages"); // Stores packages to be inserted into graph
        // Adds the names of the packages(vertices)
        for(int i = 0; i < packages.size(); i++){
            JSONObject jsonPkg = (JSONObject) packages.get(i);
            graph.addVertex((String) jsonPkg.get("name"));
            graphCopy.addVertex((String) jsonPkg.get("name"));
            JSONArray dependencies = (JSONArray) jsonPkg.get("dependencies");
            for (int j = 0; j < dependencies.size(); j++) {
                graph.addVertex((String) dependencies.get(j));
                graph.addEdge((String) jsonPkg.get("name"), (String) dependencies.get(j));
                graphCopy.addVertex((String) dependencies.get(j));
                graphCopy.addEdge((String) jsonPkg.get("name"), (String) dependencies.get(j));
            }
        }
        // Adds the dependencies(edges)
//        for(int i = 0; i < packages.size(); i++){
//            JSONObject jsonPkg = (JSONObject) packages.get(i);
//            JSONArray dependencies = (JSONArray) jsonPkg.get("dependencies");
//            for (int j = 0; j < dependencies.size(); j++)
//                graph.addEdge((String) jsonPkg.get("name"), (String) dependencies.get(j));
//        }
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        return graph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
        if (!graph.getAllVertices().contains(pkg)) {
            throw new PackageNotFoundException();
        }
        ArrayList<String> passed = new ArrayList<String>();
        Stack<String> dependents = new Stack<String>();
        if (!passed.contains(pkg)) {
            if (this.graph.getAdjacentVerticesOf(pkg).contains(pkg))
                throw new CycleException();
            getInstallationOrderForAllPackages(pkg, passed, dependents);
        }
        return dependents;
    }
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
        if (!graph.getAllVertices().contains(newPkg)
                || !graph.getAllVertices().contains(installedPkg)) {
            throw new PackageNotFoundException();
        }

        List<String> newList = this.getInstallationOrder(newPkg);
        List<String> installedList = this.getInstallationOrder(installedPkg);

        newList.removeAll(installedList);
        Collections.reverse(newList);
        return newList;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
        ArrayList<String> visited = new ArrayList<String>();
        Stack<String> dependents = new Stack<String>();

        for (String pkg : this.graph.getAllVertices()) {
            if (!visited.contains(pkg)) {
                if (this.graph.getAdjacentVerticesOf(pkg).contains(pkg)) {
                    throw new CycleException();
                }
                getInstallationOrderForAllPackages(pkg, visited, dependents);
            }
        }

        return dependents;
    }

    private void getInstallationOrderForAllPackages(String pkg,
                                                    ArrayList<String> visited, Stack<String> dependents)
            throws CycleException {
        visited.add(pkg);
        for (String pkges : this.graphCopy.getAdjacentVerticesOf(pkg)) {
            if (!visited.contains(pkges)) {
                if (this.graph.getAdjacentVerticesOf(pkges).contains(pkges)) {
                    throw new CycleException();
                }
                if (this.graph.getAdjacentVerticesOf(pkg).contains(pkges)
                        && this.graph.getAdjacentVerticesOf(pkges)
                        .contains(pkg)) {
                    throw new CycleException();
                }
                getInstallationOrderForAllPackages(pkges, visited, dependents);
            }
        }
        dependents.push(pkg);
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
        ArrayList<String> packages = new ArrayList<String>();
        packages.addAll(this.graphCopy.getAllVertices());
        int max = 0;
        String pkgMax = "";
        for (String pkg : packages) {
            try {
                if (this.getInstallationOrder(pkg).size() > max) {
                    max = this.getInstallationOrder(pkg).size();
                    pkgMax = pkg;
                }
            } catch (PackageNotFoundException e){}
        }
        return pkgMax;
    }
}

