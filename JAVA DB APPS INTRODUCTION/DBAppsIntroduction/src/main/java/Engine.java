import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Engine implements Runnable {
    private Connection connection;
    private BufferedReader reader;

    public Engine(Connection connection) {
        this.connection = connection;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    public void run() {
        try {
            //02
//            this.getVillainsName();
            //03
//            this.getMinionName();
            //04
//            this.addMinion();
//          //05
//            this.changeTownNameCasing();
            //06
//            this.removeVillain();
            //07
//            this.allMinionNames();
            //08
//            this.increaseMinionsAge();
            //09
//            this.increaseAgeStoredProcedure();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //02
    private void getVillainsName() throws SQLException {
        //option 1
        String query = "SELECT v.name, count(mv.minion_id) as count_minion FROM villains AS v JOIN minions_villains AS mv ON v.id = villain_id GROUP BY v.name HAVING count_minion > ? ORDER BY count_minion DESC;";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, 15);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("count_minion"));
        }

        //option 2
        String query2 = "SELECT v.name, count(mv.minion_id) as count_minion FROM villains AS v JOIN minions_villains AS mv ON v.id = villain_id GROUP BY v.name HAVING count_minion > 15 ORDER BY count_minion DESC;";

        Statement statement2 = connection.createStatement();

        ResultSet resultSet2 = statement2.executeQuery(query2);

        while (resultSet2.next()) {
            System.out.printf("%s %d%n", resultSet2.getString("name"), resultSet2.getInt("count_minion"));
        }
    }

    //03
    private void getMinionName() throws IOException, SQLException {
        int villainId = Integer.parseInt(this.reader.readLine());
        String queryVillainName = "SELECT v.name FROM villains AS v WHERE v.id = ?;";
        String queryMinions = "SELECT m.name, m.age FROM minions AS m JOIN minions_villains AS mv ON m.id = mv.minion_id WHERE mv.villain_id = ?;";

        PreparedStatement statementVillain = connection.prepareStatement(queryVillainName);
        statementVillain.setInt(1, villainId);

        ResultSet rsVillain = statementVillain.executeQuery();

        if (!rsVillain.isBeforeFirst()) {
            System.out.println("No villain with ID 10 exists in the database.");
        }

        while (rsVillain.next()) {
            System.out.printf("Villain: %s%n", rsVillain.getString("name"));
        }

        PreparedStatement statementMinions = connection.prepareStatement(queryMinions);
        statementMinions.setInt(1, villainId);

        ResultSet rsMinions = statementMinions.executeQuery();

        int number = 1;
        while (rsMinions.next()) {
            System.out.printf("%d. %s %s%n", number, rsMinions.getString("name"), rsMinions.getString("age"));
            number++;
        }
    }

    //04
    private void addMinion() throws IOException, SQLException {
        String[] minionData = this.reader.readLine().split("\\s+");
        String minionName = minionData[1];
        int minionAge = Integer.parseInt(minionData[2]);
        String minionTown = minionData[3];

        String villainName = this.reader.readLine().split("\\s+")[1];

        String townQuery = "select name from towns where name = ?";
        PreparedStatement townStatement = connection.prepareStatement(townQuery);
        townStatement.setString(1, minionTown);

        ResultSet townResult = townStatement.executeQuery();

        if (!townResult.isBeforeFirst()) {
            String insertQuery = "insert into towns(name, country) values (?, 'Albania')";
            PreparedStatement insertTownStatement = connection.prepareStatement(insertQuery);
            insertTownStatement.setString(1, minionTown);
            insertTownStatement.executeUpdate();
            System.out.printf("Town %s was added to the database.%n", minionTown);
        }

        String villainQuery = "select name from villains where name = ?";
        PreparedStatement villainStatement = connection.prepareStatement(villainQuery);
        villainStatement.setString(1, villainName);
        ResultSet rsVillain = villainStatement.executeQuery();

        if (!rsVillain.next()) {
            String insertQuery = "insert into villains(name, evilness_factor) values (?, 'evil')";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, villainName);
            insertStatement.executeUpdate();
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        String minionTownFinder = "select id from towns where name = ?;";
        PreparedStatement minionTownFinderStatement = connection.prepareStatement(minionTownFinder);
        minionTownFinderStatement.setString(1, minionTown);
        ResultSet resMinionTownFinder = minionTownFinderStatement.executeQuery();
        int minionTownId = 0;
        if (resMinionTownFinder.next()) {
          minionTownId = resMinionTownFinder.getInt("id");
        }

        String minionInsertQuery = "insert into minions(name, age, town_id)values(?,?,?);";
        PreparedStatement minionInsert = connection.prepareStatement(minionInsertQuery);
        minionInsert.setString(1, minionName);
        minionInsert.setInt(2, minionAge);
        minionInsert.setInt(3, minionTownId);
        minionInsert.executeUpdate();

        String minionId = "select id from minions where name = ?;";
        PreparedStatement minionIdStatement = connection.prepareStatement(minionId);
        minionIdStatement.setString(1, minionName);
        ResultSet rsMinionId = minionIdStatement.executeQuery();
        int minionIdFind = 0;
        if (rsMinionId.next()) {
            minionIdFind = rsMinionId.getInt("id");
        }

        String villainId = "select id from villains where name = ?;";
        PreparedStatement villainIdStatement = connection.prepareStatement(villainId);
        villainIdStatement.setString(1, minionName);
        ResultSet rsVillainId = villainIdStatement.executeQuery();
        int villainIdFind = 0;

        if (rsMinionId.isBeforeFirst()) {
            villainIdFind = rsVillainId.getInt("id");
        }

        Statement foreignKeyOff = connection.createStatement();
        foreignKeyOff.execute("SET FOREIGN_KEY_CHECKS=0");
        foreignKeyOff.close();

        String insertMinionToVillain = "insert into minions_villains values (?,?);";
        PreparedStatement insertMinion = connection.prepareStatement(insertMinionToVillain);
        insertMinion.setInt(1, minionIdFind);
        insertMinion.setInt(2, villainIdFind);
        insertMinion.executeUpdate();
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);

        Statement foreignKeyOn = connection.createStatement();
        foreignKeyOn.execute("SET FOREIGN_KEY_CHECKS=1");
        foreignKeyOn.close();
    }

    //05
    private void changeTownNameCasing() throws IOException, SQLException {
        String country = this.reader.readLine();
        String numberOfTowns = "select count(name) as number from towns where country = ? group by country;";

        PreparedStatement statement = connection.prepareStatement(numberOfTowns);
        statement.setString(1, country);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            System.out.println("No town names were affected.");
            return;
        }

        while (resultSet.next()) {
            System.out.printf("%d town names were affected.%n", resultSet.getInt("number"));
        }

        String upperNameTowns = "select  UPPER(name) as name from towns where country = 'Bulgaria';";
        Statement statement2 = connection.createStatement();
        ResultSet rsTownsNames = statement2.executeQuery(upperNameTowns);

        //option 1
        StringBuilder sb = new StringBuilder();
        while (rsTownsNames.next()) {
            if (rsTownsNames.isLast()) {
                sb.append(rsTownsNames.getString("name"));
            } else {
                sb.append(rsTownsNames.getString("name")).append(", ");
            }
        }
        System.out.println("[" + sb + "]");

        //option 2
        while (rsTownsNames.next()) {
            if (rsTownsNames.isFirst()) {
                System.out.print("[" + rsTownsNames.getString("name") + ", ");
            } else if (rsTownsNames.isLast()) {
                System.out.println(rsTownsNames.getString("name") + "]");
            } else {
                System.out.print(rsTownsNames.getString("name") + ", ");
            }
        }
    }

    //06*
    private void removeVillain() throws SQLException, IOException {
        int villainID = Integer.parseInt(this.reader.readLine());
        String findVillain = "select v.name, count(mv.minion_id) as minions from villains as v join minions_villains as mv on v.id = mv.villain_id where v.id = ? group by mv.villain_id;";

        PreparedStatement statement = connection.prepareStatement(findVillain);
        statement.setInt(1, villainID);

        ResultSet rs = statement.executeQuery();

        if (!rs.isBeforeFirst()) {
            System.out.println("No such villain was found");
        } else {
            while (rs.next()) {
                System.out.printf("%s was deleted%n%s minions released", rs.getString("name"), rs.getString("minions"));
            }
            String deleteVillain = "delete from minions_villains where villain_id = ?;";
            String deleteVillain2 = "delete from villains where id = ?;";
            PreparedStatement s1 = connection.prepareStatement(deleteVillain);
            s1.setInt(1, villainID);
            s1.executeUpdate();
            PreparedStatement s2 = connection.prepareStatement(deleteVillain2);
            s2.setInt(1, villainID);
            s2.executeUpdate();
        }
    }

    //07
    private void allMinionNames() throws SQLException {
        String query = "select name from minions;";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(query);

        List<String> allMinionNames = new ArrayList<String>();

        while (rs.next()) {
            allMinionNames.add(rs.getString("name"));
        }

        for (int i = 0; i < allMinionNames.size() / 2; i++) {
            System.out.println(allMinionNames.get(i));
            System.out.println(allMinionNames.get(allMinionNames.size() - 1 - i));
        }
    }

    //08
    private void increaseMinionsAge() throws IOException, SQLException {
        List<Integer> minionsAge = Arrays.stream(this.reader.readLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());

        String query = "update minions  set name = lower(name), age = age + 1 where id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);

        for (Integer integer : minionsAge) {
            statement.setInt(1, integer);
            statement.executeUpdate();
        }

        String allMinions = "select name, age from minions";
        Statement state = connection.createStatement();
        ResultSet printAll = state.executeQuery(allMinions);


        while (printAll.next()) {
            System.out.printf("%s %s%n", printAll.getString("name"), printAll.getString("age"));
        }
    }

    //09 процедурата остава в базата, май.
//    create
//            definer = root@localhost procedure usp_get_older(IN minion_id int)
//    begin
//    Update minions set age = age + 1 where id = minion_id;
//    select name, age from minions where id = minion_id;
//    end;
    private void increaseAgeStoredProcedure() throws SQLException, IOException {
        String query = "call usp_get_older(?);";

        CallableStatement statement = connection.prepareCall(query);
        statement.setInt(1, Integer.parseInt(this.reader.readLine()));
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.printf("%s %s%n", rs.getString("name"), rs.getString("age"));
        }
    }

}
