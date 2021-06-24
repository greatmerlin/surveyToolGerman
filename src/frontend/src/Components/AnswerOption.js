import React from "react";
import { Form } from "react-bootstrap";
import DeleteButtonLight from "./DeleteButtonLight";

/**
 * Input element. User can register an answer option for a question.
 *
 * @param {removeAnswerOption} function
 * @param {updateAnswerOption} function
 * @param {answerOptionValue} function
 * @param {answerOptionIndex} number
 * @returns JSX.Element
 */

export default function AnswerOption({
  removeAnswerOption,
  updateAnswerOption,
  answerOptionValue,
  answerOptionIndex,
}) {
  return (
    <Form.Group controlId={answerOptionIndex}>
      <div className="d-flex flex-wrap">
        <Form.Control
          type="text"
          placeholder="Erfasse hier eine passende Antwort."
          onChange={updateAnswerOption}
          value={answerOptionValue}
          required
          style={{ flex: "1 0 content" }}
        />
        <DeleteButtonLight hint="Antwort löschen" action={removeAnswerOption} />
        <Form.Control.Feedback type="invalid" style={{ flex: "0 0 100%" }}>
          Das Feld darf nicht leer sein.
        </Form.Control.Feedback>
      </div>
    </Form.Group>
  );
}
