import org.json.simple.parser.ParseException;
import org.junit.After;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PackageManagerTest {

    PackageManager packageManager;

    /**
     * Initialize empty graph to be used in each test
     */
    @BeforeEach
    public void setUp() throws Exception {
        packageManager = new PackageManager();
    }

    /**
     * Not much to do, just make sure that variables are reset
     */
    @AfterEach
    public void tearDown() throws Exception {
        packageManager = null;
    }

    @Test
    public void test00_Construct_Graph() throws IOException, ParseException {
        packageManager.constructGraph("valid.json");
        Set<String> packages = new HashSet<>();
        packages.add("A");
        packages.add("B");
        packages.add("C");
        packages.add("D");
        packages.add("E");
        if (!packages.equals(packageManager.getAllPackages()))
            fail("Not all packages were added to the graph, you have: " + packageManager.getAllPackages() + " instead of: " + packages);
    }

    @Test
    public void test01_Get_Installation_Order() throws IOException, ParseException {
        packageManager.constructGraph("valid.json");
        List<String> packages = new ArrayList<>();
    }
}
