import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, MouseListener{

    DB_Manager db_manager;
    Score score;

    JLabel lbName, lbKor, lbEng, lbMath;
    JTextField tfName, tfKor, tfEng, tfMath;
    JButton jbAdd, jbDel, jbChange;
    JTable table;
    Vector<String> col;

    GUI(){
        db_manager = new DB_Manager();
        setLayout(null);

        add(lbName = new JLabel("이름", JLabel.CENTER));
        lbName.setBorder(BorderFactory.createBevelBorder(0));
        lbName.setBounds(10, 10, 120,50);
        add(tfName = new JTextField());
        tfName.setHorizontalAlignment(JTextField.CENTER);
        tfName.setBounds(140, 10, 120,50);

        add(lbKor = new JLabel("국어점수", JLabel.CENTER));
        lbKor.setBorder(BorderFactory.createBevelBorder(0));
        lbKor.setBounds(10, 70, 120,50);
        add(tfKor = new JTextField());
        tfKor.setHorizontalAlignment(JTextField.CENTER);
        tfKor.setBounds(140, 70, 120,50);

        add(lbEng = new JLabel("영어 점수", JLabel.CENTER));
        lbEng.setBorder(BorderFactory.createBevelBorder(0));
        lbEng.setBounds(10, 130, 120,50);
        add(tfEng = new JTextField());
        tfEng.setHorizontalAlignment(JTextField.CENTER);
        tfEng.setBounds(140, 130, 120,50);

        add(lbMath = new JLabel("수학 점수", JLabel.CENTER));
        lbMath.setBorder(BorderFactory.createBevelBorder(0));
        lbMath.setBounds(10, 190, 120,50);
        add(tfMath = new JTextField());
        tfMath.setHorizontalAlignment(JTextField.CENTER);
        tfMath.setBounds(140, 190, 120,50);

        add(jbAdd = new JButton("추가"));
        jbAdd.setBounds(270, 10, 120, 50);
        jbAdd.addActionListener(this);

        add(jbAdd = new JButton("삭제"));
        jbAdd.setBounds(270, 70, 120, 50);
        jbAdd.addActionListener(this);

        add(jbAdd = new JButton("수정"));
        jbAdd.setBounds(270, 130, 120, 50);
        jbAdd.addActionListener(this);

        col = new Vector<String>();
        col.add("이름");
        col.add("국어 점수");
        col.add("영어 점수");
        col.add("수학 점수");
        col.add("평균");
        col.add("총점");

        DefaultTableModel model = new DefaultTableModel(db_manager.getScore(), col){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table = new JTable(model);
        table.addMouseListener(this);
        JScrollPane scroll = new JScrollPane(table);
        jTableSet();
        add(scroll);
        scroll.setBounds(415,10,770,250);

        setResizable(false);
        setSize(1200,300);
        setTitle("성적 관리 프로그램");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ButtonFlag = e.getActionCommand();
        Score score = new Score();

        if(ButtonFlag.equals("추가")){
            try{
                contentSet(score);
                int result = db_manager.insertScore(score);

                if(result == 1){
                    JOptionPane.showMessageDialog(this, "추가 되었습니다.");
                    jTableRefresh();
                    contentClear();
                }else{
                    JOptionPane.showMessageDialog(this, "추가에 실패했습니다.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "이름 혹은 성적을 입력하여주십시오.");
            }
        }

        if(ButtonFlag.equals("삭제")){
            try{
                contentSet(score);
                int result = db_manager.deleteScore(score);

                if(result == 1){
                    JOptionPane.showMessageDialog(this, "삭제 되었습니다.");
                    jTableRefresh();
                    contentClear();
                }else{
                    JOptionPane.showMessageDialog(this, "삭제에 실패했습니다.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "이름을 입력하여주십시오.");
            }
        }

        if(ButtonFlag.equals("수정")){
            try{
                contentSet(score);
                int result = db_manager.updateScore(score);

                if(result == 1){
                    JOptionPane.showMessageDialog(this, "갱신 되었습니다.");
                    jTableRefresh();
                    contentClear();
                }else{
                    JOptionPane.showMessageDialog(this, "갱신에 실패했습니다.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "이름을 입력하여주십시오.");
            }
        }
    }

    public void jTableSet() {
        // 이동과 길이조절 여러개 선택 되는 것을 방지한다
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        // 컬럼 정렬에 필요한 메서드
        DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
        celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

        // 컬럼별 사이즈 조절 & 정렬
        for(int i=0; i< table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(10);
            table.getColumnModel().getColumn(i).setCellRenderer(celAlignCenter);
        }
    }

    public void contentClear(){
        tfName.setText("");
        tfKor.setText("");
        tfEng.setText("");
        tfMath.setText("");
    }

    public void contentSet(Score score) {
        String name = tfName.getText();
        int kor = Integer.parseInt(tfKor.getText());
        int eng = Integer.parseInt(tfEng.getText());
        int math = Integer.parseInt(tfMath.getText());
        int total = kor + eng + math;
        int average = total/3;

        score.setName(name);
        score.setKor(kor);
        score.setEng(eng);
        score.setMath(math);
        score.setTotal(total);
        score.setAverage(average);
    }

    public void jTableRefresh(){
        DefaultTableModel model = new DefaultTableModel(db_manager.getScore(), col){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setModel(model);
        jTableSet();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int rowIndex = table.getSelectedRow();
        tfName.setText(table.getValueAt(rowIndex, 0) + "");
        tfKor.setText(table.getValueAt(rowIndex, 1) + "");
        tfEng.setText(table.getValueAt(rowIndex, 2) + "");
        tfMath.setText(table.getValueAt(rowIndex, 3) + "");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
