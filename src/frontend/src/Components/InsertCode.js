import React, { useState, useEffect } from "react";
import { Alert, Button, Form } from "react-bootstrap";

/**
 * Component handles missing or invalid urls for participation and evaluation.
 *
 * @param {url} string, location for redirection
 * @param {urlId} uuid
 * @returns JSX.Element
 */

export default function InsertCode({ url, urlId }) {
  const [id, setId] = useState("");
  const [validated, setValidated] = useState(false);
  const [showError, setShowError] = useState(true);

  useEffect(() => {
    setId(urlId);
    setValidated(true);
  }, [urlId]);

  const submitForm = (e) => {
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }
  };

  const updateId = (e) => {
    setId(e.target.value);
    setShowError(false);
  };

  return (
    <Form
      id="submit-form"
      noValidate
      onSubmit={submitForm}
      validated={validated}
      action={"/" + url + "/" + id}
      style={{ marginTop: "25vh" }}
    >
      <Form.Group controlId="code-input">
        <Form.Label className="h5">Code</Form.Label>
        <Form.Control
          value={id}
          onChange={updateId}
          required
          pattern="^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$"
        />
        <Form.Control.Feedback type="invalid">
          Der Code ist in einem falschen Format. Bitte verwende das Format
          'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx'.
        </Form.Control.Feedback>
      </Form.Group>
      {showError && (
        <Alert variant="danger" className="mt-3">
          Der Code ist leider nicht gültig.
        </Alert>
      )}
      <Form.Group className="d-flex justify-content-end mt-3">
        <Button variant="link" className="text-white" href="/selectPath">
          Abbrechen
        </Button>
        <Button variant="primary" type="submit">
          Code absenden
        </Button>
      </Form.Group>
    </Form>
  );
}
