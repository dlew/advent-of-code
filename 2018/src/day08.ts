export function sumMetadata(numbers: Array<number>): number {
  const root = parseNode(numbers).node;
  return sumMetadataRecursive(root);
}

function sumMetadataRecursive(node: LicenseNode): number {
  let value = node.metadata.reduce((acc, n) => acc + n, 0);
  for (const child of node.children) {
    value += sumMetadataRecursive(child);
  }
  return value;
}

export function sumMetadataComplex(numbers: Array<number>): number {
  const root = parseNode(numbers).node;
  return sumMetadataComplexRecursive(root);
}

function sumMetadataComplexRecursive(node: LicenseNode): number {
  // Easy case - no children - sum metadata
  if (node.children.length === 0) {
    return node.metadata.reduce((acc, n) => acc + n, 0);
  }

  // Hard case - has children - reference metadata entries
  let value = 0;
  for (const entry of node.metadata) {
    const child = node.children[entry - 1];
    if (child !== undefined) {
      value += sumMetadataComplexRecursive(child);
    }
  }
  return value;
}

function parseNode(
  numbers: Array<number>,
  start = 0
): { node: LicenseNode; end: number } {
  // Parse header
  let index = start;
  const numChildren = numbers[index];
  const numMetadata = numbers[index + 1];

  // Parse children
  index += 2;
  const children = new Array<LicenseNode>(numChildren);
  for (let child = 0; child < numChildren; child++) {
    const { node, end } = parseNode(numbers, index);
    children[child] = node;
    index = end;
  }

  // Parse metadata
  const metadata = numbers.slice(index, index + numMetadata);
  index += numMetadata;

  return {
    node: new LicenseNode(children, metadata),
    end: index,
  };
}

class LicenseNode {
  constructor(
    readonly children: Array<LicenseNode>,
    readonly metadata: Array<number>
  ) {}
}
