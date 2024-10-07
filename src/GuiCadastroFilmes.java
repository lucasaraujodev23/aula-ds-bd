import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GuiCadastroFilmes extends JFrame {
    JLabel label1, label2, label3, label4, label5;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfCodigo, tfTitulo, tfGenero, tfProdutora, tfDataCompra;
    private FilmesDAO filmes;

    public static void main(String[] args) {
        JFrame janela = new GuiCadastroFilmes();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
    public GuiCadastroFilmes() {
        inicializarComponentes();
        definirEventos();
    }
    public void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setTitle("Cadastro de Filmes");
        setBounds(200, 100, 650, 120);
        label1 = new JLabel("Código");
        label2 = new JLabel("Título");
        label3 = new JLabel("Gênero");
        label4 = new JLabel("Produtora");
        label5 = new JLabel("Data de Compra   ");
        tfCodigo = new JTextField(10);
        tfTitulo = new JTextField(35);
        tfGenero = new JTextField(10);
        tfProdutora = new JTextField(15);
        tfDataCompra = new JTextField(8);
        btGravar = new JButton(null, new ImageIcon("#"));
        btGravar.setToolTipText("Próximo");
        btAlterar = new JButton(null, new ImageIcon("#"));
        btAlterar.setToolTipText("Anterior");
        btExcluir = new JButton(null, new ImageIcon("#"));
        btExcluir.setToolTipText("Primeiro");
        btLocalizar = new JButton(null, new ImageIcon("#"));
        btLocalizar.setToolTipText("Último");
        btNovo = new JButton(null, new ImageIcon("#"));
        btNovo.setToolTipText("+10");
        btCancelar = new JButton(null, new ImageIcon("#"));
        btCancelar.setToolTipText("-10");
        btSair = new JButton(null, new ImageIcon("#"));
        btSair.setToolTipText("Sair");
        add(label1);
        add(tfCodigo);
        add(label2);
        add(tfTitulo);
        add(label3);
        add(tfGenero);
        add(label4);
        add(tfProdutora);
        add(label5);
        add(tfDataCompra);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);
        setResizable(false);
        setBotoes(true, true, false, false, false, false);
        filmes = new FilmesDAO();
        if (!filmes.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }
    public void definirEventos() {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filmes.bd.close();
                System.exit(0);
            }
        });
        btNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                setBotoes(false, false, true, false, false, true);
            }
        });
        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tfCodigo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O código não pode ser vazio!");
                    tfCodigo.requestFocus();
                    return;
                }
                if (tfTitulo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O título não pode ser vazio!");
                    tfTitulo.requestFocus();
                    return;
                }
                if (tfGenero.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O gênero não pode ser vazio!");
                    tfGenero.requestFocus();
                    return;
                }
                if (tfProdutora.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "A produtora não pode ser vazia!");
                    tfProdutora.requestFocus();
                    return;
                }
                if (tfDataCompra.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "A data de compra não pode ser vazia!");
                    tfDataCompra.requestFocus();
                    return;
                }
                filmes.filme.setCodigo(tfCodigo.getText());
                filmes.filme.setTitulo(tfTitulo.getText());
                filmes.filme.setGenero(tfGenero.getText());
                filmes.filme.setProdutora(tfProdutora.getText());
                filmes.filme.setDataCompra(tfDataCompra.getText());
                JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.INCLUSAO));
                limparCampos();
            }
        });
        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filmes.filme.setCodigo(tfCodigo.getText());
                filmes.filme.setTitulo(tfTitulo.getText());
                filmes.filme.setGenero(tfGenero.getText());
                filmes.filme.setProdutora(tfProdutora.getText());
                filmes.filme.setDataCompra(tfDataCompra.getText());
                JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.ALTERACAO));
                limparCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filmes.filme.setCodigo(tfCodigo.getText());
                filmes.localizar();
                int n = JOptionPane.showConfirmDialog(null, filmes.filme.getTitulo(),
                        "Excluir o Filme? ", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, filmes.atualizar(FilmesDAO.EXCLUSAO));
                    limparCampos();
                }
            }
        });
        btLocalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
            }
        });
    }
    public void limparCampos() {
        tfCodigo.setText("");
        tfTitulo.setText("");
        tfGenero.setText("");
        tfProdutora.setText("");
        tfDataCompra.setText("");
        tfCodigo.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }
    public void atualizarCampos() {
        filmes.filme.setCodigo(tfCodigo.getText());
        if (filmes.localizar()) {
            tfCodigo.setText(filmes.filme.getCodigo());
            tfTitulo.setText(filmes.filme.getTitulo());
            tfGenero.setText(filmes.filme.getGenero());
            tfProdutora.setText(filmes.filme.getProdutora());
            tfDataCompra.setText(filmes.filme.getDataCompra());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Filme não encontrado!");
            limparCampos();
        }
    }
    public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar,
                          boolean bAlterar, boolean bExcluir, boolean bCancelar) {
        btNovo.setEnabled(bNovo);
        btLocalizar.setEnabled(bLocalizar);
        btGravar.setEnabled(bGravar);
        btAlterar.setEnabled(bAlterar);
        btExcluir.setEnabled(bExcluir);
        btCancelar.setEnabled(bCancelar);
    }
}