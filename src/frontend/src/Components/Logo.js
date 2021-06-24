import React from "react";

/**
 * Renders Answerium logo.
 *
 * @returns JSX.Element
 */

export default function Logo() {
  return (
    <svg
      data-name="Answerium-Logo"
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 353.55 282.84"
      height="50"
    >
      <linearGradient id="answerium-gradient" x2="1" y2="1">
        <stop offset="0%" stopColor="#007bff" />
        <stop offset="100%" stopColor="#e83e8c" />
      </linearGradient>
      <path
        d="M318.2 176.78l-35.36-35.36 35.36-35.35 35.35-35.36-35.35-35.36-35.36 35.36-35.35 35.36-35.36-35.36-35.35-35.36L141.42 0l-35.35 35.35 35.35 35.36 35.36 35.36 35.35 35.35-35.35 35.36-35.36 35.35-35.35-35.35-35.36-35.36 35.36-35.35-35.36-35.36-35.36 35.36L0 141.42l35.35 35.36 35.36 35.35 35.36 35.36 35.35 35.35 35.36-35.35 35.35-35.36 35.36-35.35 35.35 35.35 35.36 35.36 35.35-35.36-35.35-35.35z"
        fill="url(#answerium-gradient)"
      />
    </svg>
  );
}
