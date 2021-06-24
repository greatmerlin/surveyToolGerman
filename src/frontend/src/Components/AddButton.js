import React from "react";
import { Button } from "react-bootstrap";

/**
 * Draws a plus (+) symbol on a button.
 *
 * @param {action} function which will run when button is clicked.
 * @param {hint} string which will be displayed while hovering over button.
 * @returns JSX.Element
 */

export default function AddButton({ action, hint }) {
  return (
    <Button
      className="position-relative"
      style={{
        borderRadius: "50%",
        height: "40px",
        width: "40px",
      }}
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
        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
      </svg>
    </Button>
  );
}
