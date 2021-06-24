import React from "react";

/**
 * Renders the title of the page.
 *
 * @param {children} JSX.Element
 * @returns JSX.Element
 */

export default function Title({ children }) {
  return <h2 className="mb-4">{children}</h2>;
}
