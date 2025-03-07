const fs = require('fs-extra');
const path = require('path');

const source = path.join(__dirname, '..', 'dist', 'spring-boot-angular-starter-kit', 'browser');
const destination = path.join(__dirname, '..', '..', 'resources', 'static');

async function copyBuild() {
  try {
    // Remove destination folder if it exists
    if (fs.existsSync(destination)) {
      await fs.remove(destination);
    }
    // Recreate destination folder
    await fs.ensureDir(destination);
    // Copy Angular build files
    await fs.copy(source, destination);
    console.log('Build copied successfully!');
  } catch (err) {
    console.error('Error during postbuild:', err);
  }
}

copyBuild();
