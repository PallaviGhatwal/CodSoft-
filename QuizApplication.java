import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication {
    private int score = 0;
    private int currentQuestion = 0;
    private int timeLeft = 15; // Timer per question
    private Timer timer;

    private JFrame frame;
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel timerLabel;

    private String[][] questions = {
            {"What does CPU stand for?", "Central Processing Unit", "Central Program Unit", "Computer Personal Unit", "Central Processor Utility", "Central Processing Unit"},
            {"Which programming language is known as the 'mother of all languages'?", "C", "Python", "Java", "Assembly", "C"},
            {"Which company developed the Java programming language?", "Microsoft", "Sun Microsystems", "Google", "Apple", "Sun Microsystems"},
            {"What does HTTP stand for?", "HyperText Transfer Protocol", "HyperText Transmission Protocol", "High Text Transfer Protocol", "High Transfer Text Protocol", "HyperText Transfer Protocol"},
            {"Which device is used to connect a computer to a network?", "Router", "CPU", "Monitor", "Printer", "Router"}
    };

    public QuizApplication() {
        // Create JFrame
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Larger GUI size
        frame.setLayout(new BorderLayout());

        // Question Label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        frame.add(questionLabel, BorderLayout.NORTH);

        // Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].addActionListener(new OptionButtonListener());
            optionsPanel.add(optionButtons[i]);
        }
        frame.add(optionsPanel, BorderLayout.CENTER);

        // Timer Label
        timerLabel = new JLabel("Time left: " + timeLeft, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(timerLabel, BorderLayout.SOUTH);

        // Start the quiz
        displayNextQuestion();

        frame.setVisible(true);
    }

    private void displayNextQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText("Q" + (currentQuestion + 1) + ": " + questions[currentQuestion][0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questions[currentQuestion][i + 1]);
                optionButtons[i].setEnabled(true);
            }
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timeLeft = 15; // Reset time for each question
        timerLabel.setText("Time left: " + timeLeft);
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(frame, "Time's up! The correct answer was: " + questions[currentQuestion][5]);
                    currentQuestion++;
                    displayNextQuestion();
                }
            }
        });
        timer.start();
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equals(questions[currentQuestion][5])) {
            score++;
            JOptionPane.showMessageDialog(frame, "Correct!");
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect! The correct answer was: " + questions[currentQuestion][5]);
        }
        currentQuestion++;
        displayNextQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(frame, "Quiz Over!\n" +
                "Total Questions: " + questions.length + "\n" +
                "Correct Answers: " + score + "\n" +
                "Incorrect Answers: " + (questions.length - score));
        frame.dispose();
    }

    private class OptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String selectedAnswer = source.getText();
            timer.stop();
            checkAnswer(selectedAnswer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApplication::new);
    }
}
