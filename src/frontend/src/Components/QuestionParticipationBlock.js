import React from "react";
import { Card, Form } from "react-bootstrap";
import { QUESTION_TYPES } from "../Config";

/**
 * Renders a question for the user to participate.
 *
 * @param {question} object, which consists of {question as string}, {questionType as enum (MULTIPLE_CHOICE or MULTISELECT)} and {answerOptions as list}.
 * @param {questionIndex} number
 * @param {updateChoosen} function, to handle when user changes choosen answers
 * @returns JSX.Element
 */

export default function QuestionParticipationBlock({
  question,
  questionIndex,
  updateChoosen,
}) {
  const type =
    question.questionType === QUESTION_TYPES.MULTIPLE_CHOICE
      ? "radio"
      : "checkbox";

  return (
    <Card>
      <Card.Body>
        <Card.Title>{question.question}</Card.Title>
        <Form.Text className="text-muted mb-3">
          {question.questionType === QUESTION_TYPES.MULTIPLE_CHOICE
            ? "Wähle exakt eine Antwort."
            : "Wähle 0, 1 oder mehrere Antworten."}
        </Form.Text>
        <Form.Group>
          {question.answerOptions.map((answerOption, i) => (
            <Form.Check
              type={type}
              id={`${questionIndex}-${i}`}
              value={answerOption.choosen}
              onChange={() => updateChoosen(answerOption.answerOptionId)}
              name={questionIndex}
              label={answerOption.answerOption}
              key={i}
              required={type === "radio"}
            />
          ))}
        </Form.Group>
      </Card.Body>
    </Card>
  );
}
