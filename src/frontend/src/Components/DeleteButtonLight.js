import React from "react";
import { Button } from "react-bootstrap";

/**
 * Draws a cross (x) symbol on a button.
 *
 * @param {action} function which will run when button is clicked.
 * @param {hint} string which will be displayed while hovering over button.
 * @returns JSX.Element
 */

export default function DeleteButtonLight({ action, hint }) {
  return (
    <Button
      className="position-relative text-danger"
      style={{
        borderRadius: "50%",
        height: "40px",
        width: "40px",
      }}
      variant="link"
      onClick={action}
      data-toggle="tooltip"
      data-placement="bottom"
      title={hint}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        height="40"
        fill="currentColor"
        viewBox="0 0 16 16"
        className="position-absolute"
        style={{
          top: 0,
          left: 0,
          bottom: 0,
          right: 0,
        }}
      >
        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z" />
      </svg>
    </Button>
  );
}
