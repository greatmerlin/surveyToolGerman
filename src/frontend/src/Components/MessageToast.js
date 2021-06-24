import React from "react";
import { Toast } from "react-bootstrap";

/**
 * Renders message to user. Can be used to show errors or other messages.
 *
 * @param {visibility} boolean, if toast should be visible.
 * @param {onClose} function, to handle when user clicks on close button.
 * @param {error} boolean, if this is an error message, default is {true}.
 * @param {body} string, message.
 * @returns JSX.Element
 */

export default function MessageToast({
  visibility,
  onClose,
  error = true,
  body,
}) {
  const title = error ? "Leider ist ein Fehler aufgetreten" : "Erfolg";

  return (
    <div
      className="position-absolute"
      style={{ left: "2rem", bottom: "5rem", zIndex: 20 }}
    >
      <Toast
        show={visibility}
        onClose={onClose}
        animation={false}
        style={{ backgroundColor: "rgb(255, 255, 255)" }}
      >
        <Toast.Header>
          <strong
            className={`mr-auto ${error ? "text-danger" : "text-success"}`}
          >
            {title}
          </strong>
        </Toast.Header>
        <Toast.Body>
          <pre>{body}</pre>
          {error && <small>Melde dich bitte bei den Administratoren.</small>}
        </Toast.Body>
      </Toast>
    </div>
  );
}
