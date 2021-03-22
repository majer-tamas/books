export function getErrorMessage(error: any): string {
  return error.error.details;
}
