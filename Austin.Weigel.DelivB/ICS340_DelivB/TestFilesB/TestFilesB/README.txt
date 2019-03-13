README - Test Files for Program Deliverable B
=============================================
There are 60 points for correct test execution on Program Deliverable B.  The program will be tested against 5 of these files, 10 points per test.  The other two, prefixed with the label "fun", may be useful or interesting but won't be tested.  Probably the easiest ones, because ther are no ties, are files AB0.txt and ABD6.txt.

Here are the files:

AB0.txt:  This is the file from the Prog340 handout.  It has Strings for edge values.  It has one SCC.

ABslides.txt:  This is the file from the "search" slides of January 28th.  The edges are all integers with length 1.  Therefore, there are ties.  There are three SCCs. 

ABhw2.txt:  This is the file from Homework 2.  It has integer edge values with ties.  There are multiple SCCs (part of HW-W2 is to figure out what they are).

ABD6.txt:  This is a file of 12 cities with edges between each pair of cities in both directions.  Thus it has integer edge values.  It has no ties.  It has one SCC.  This is maybe the simplest graph of all for you to work with.   

ABafc.txt:  In a nod to the Super Bowl, this is a file with 16 nodes, representing the 16 teams of the AFC in the NFL.  Each team (node) has a connection to each other team (node) in its division, so this yields four SCCs.  

Additional files:

These will not be graded.

ABnfc.txt:  While I was at it, I made up a similar files for NFL NFC teams, using integer valued edges with ties.  (Value for any incoming edge to a team = number of wins that team had in the regular season).  You should get four SCCs again for this one.

ABD2.txt:  Huge file with 49 nodes.  Integer edge values.  One SCC.  Basically a road distance map for the state of Minnesota.  Won't test this now, but you should be able to read it, because it will be a test file for Program Deliverable D.  If your program is designed correctly, this should run without any change in your program.  If it doesn't run and the others do, try to figure out why large files don't work on your system.