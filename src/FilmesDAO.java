import java.sql.*;

public class FilmesDAO {
    public Filmes filme;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;

    public FilmesDAO() {
        bd = new BD();
        filme = new Filmes();
    }
    public boolean localizar() {
        sql = "SELECT * FROM filmes WHERE codigo = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, filme.getCodigo());
            resultSet = statement.executeQuery();
            resultSet.next();
            filme.setCodigo(resultSet.getString(1));
            filme.setTitulo(resultSet.getString(2));
            filme.setGenero(resultSet.getString(3));
            filme.setProdutora(resultSet.getString(4));
            filme.setDataCompra("" + resultSet.getString(5));
        } catch (SQLException erro) {
            return false;
        }
        return localizar();
    }
    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if (operacao == INCLUSAO) {
                sql = "INSERT INTO filmes VALUES (?,?,?,?,?)";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, filme.getCodigo());
                statement.setString(2, filme.getTitulo());
                statement.setString(3, filme.getGenero());
                statement.setString(4, filme.getProdutora());
                statement.setString(5, filme.getDataCompra());
            } else if (operacao == ALTERACAO) {
                sql = "UPDATE filmes SET titulo = ?, genero = ?, produtora = ?, "
                        + "datacompra = ? WHERE codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, filme.getCodigo());
                statement.setString(2, filme.getTitulo());
                statement.setString(3, filme.getGenero());
                statement.setString(4, filme.getProdutora());
                statement.setString(5, filme.getDataCompra());
            } else if (operacao == EXCLUSAO) {
                sql = "DELETE FROM filmes WHERE codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, filme.getCodigo());
            }
            if (statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return men;
    }
}
