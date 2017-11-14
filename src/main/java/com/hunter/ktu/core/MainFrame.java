package com.hunter.ktu.core;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class MainFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);
    private static final String MENU_LIST = "kafka.topic.list";

    public static void run() {
        try {
            UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());// 还可以
        } catch (Exception e) {
        }
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Kafka Test UI");
        frame.setSize(500, 530);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - frame.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel topicLabel = new JLabel("Topic:");
        topicLabel.setBounds(10, 20, 80, 25);
        panel.add(topicLabel);
        final JComboBox<Object> topicBox = new JComboBox<Object>(getMenuList());
        topicBox.setEditable(true);
        topicBox.setBounds(100, 20, 300, 25);
        panel.add(topicBox);

        JLabel jsonLabel = new JLabel("JSON:");
        jsonLabel.setBounds(10, 50, 80, 25);
        panel.add(jsonLabel);
        final JTextArea jsonText = new JTextArea();

        JScrollPane scroll = new JScrollPane(jsonText);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scroll.setBounds(100, 50, 300, 350);
        jsonText.setLineWrap(true);
        panel.add(scroll);

        final JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(100, 420, 300, 25);
        panel.add(resultLabel);

        JButton loginButton = new JButton("GO");
        loginButton.setBounds(100, 460, 80, 25);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new Thread(new Runnable() {
                    public void run() {
                        resultLabel.setText("");
                        JSONObject jsonValue = JSONObject.parseObject(jsonText.getText().toString());
                        String topic = topicBox.getSelectedItem().toString();
                        LOGGER.info("开始发送kafka消息,topic:{},jsonValue:{}", topic, jsonValue);
                        Map<String, Object> map = App.kafkaUtil.sendMesForTemplate(topic, jsonValue, "1", 8,
                                System.currentTimeMillis() + "");
                        LOGGER.info("发送kafka消息完成,result:{}", map);
                        resultLabel.setText(map.toString());
                    }
                }).start();
            }
        });

        jsonText.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
            }

            public void focusGained(FocusEvent e) {
                resultLabel.setText("");
            }
        });

        topicBox.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
            }

            public void focusGained(FocusEvent e) {
                resultLabel.setText("");
            }
        });

        panel.add(loginButton);
    }

    /**
     * 函数的目的/功能
     * 
     * @return
     */
    private static Object[] getMenuList() {
        try {
            return CustomizedPropertyUtil.getContextProperty(MENU_LIST).split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[]{};
    }

}
