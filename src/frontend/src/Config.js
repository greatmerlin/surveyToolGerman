export const HOST =
  window.location.hostname === "localhost"
    ? "http://localhost:8181"
    : `https://${window.location.hostname}`;
export const QUESTION_TYPES = {
  MULTIPLE_CHOICE: "MULTIPLE_CHOICE",
  MULTISELECT: "MULTISELECT",
};
