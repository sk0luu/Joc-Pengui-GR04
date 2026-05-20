import os
import re

base_dir = "/Users/gerardlechosa/Documents/Github/Joc-Pengui-GR04/jocpinguiFinal/src/jocpinguiFinal"

# Regex patterns
class_pattern = re.compile(r'^(\s*)(public\s+|abstract\s+|final\s+)*(class|interface)\s+(\w+)')
method_pattern = re.compile(r'^(\s*)(public|private|protected)?\s*(static\s+)?([\w<>,\[\]]+)\s+(\w+)\s*\((.*?)\)\s*(?:throws\s+[\w,\s]+)?\s*[{;]')
constructor_pattern = re.compile(r'^(\s*)(public|private|protected)\s+(\w+)\s*\((.*?)\)\s*(?:throws\s+[\w,\s]+)?\s*\{')
field_pattern = re.compile(r'^(\s*)(private|protected|public)?\s*(static\s+)?(final\s+)?([\w<>,\[\]]+)\s+(\w+)(?:\s*=\s*[^;]+)?\s*;')

for root, dirs, files in os.walk(base_dir):
    for file in files:
        if file.endswith(".java"):
            filepath = os.path.join(root, file)
            with open(filepath, "r", encoding="utf-8") as f:
                lines = f.readlines()
            
            new_lines = []
            i = 0
            while i < len(lines):
                line = lines[i]
                stripped = line.strip()
                
                # Check if it's already commented
                is_commented = False
                if i > 0 and (lines[i-1].strip().startswith("//") or lines[i-1].strip().endswith("*/")):
                    is_commented = True
                if stripped.startswith("@") or stripped.startswith("//") or stripped.startswith("/*") or stripped.startswith("*") or stripped == "":
                    new_lines.append(line)
                    i += 1
                    continue
                
                # Match method
                m_match = method_pattern.match(line)
                if m_match and not is_commented and " return " not in line and " else " not in line:
                    indent = m_match.group(1)
                    ret_type = m_match.group(4)
                    name = m_match.group(5)
                    args = m_match.group(6)
                    
                    if name.startswith("get"):
                        comment = f"{indent}// metodo que devuelve el valor de {name[3:].lower()}"
                    elif name.startswith("set"):
                        comment = f"{indent}// metodo que actualiza o establece el valor de {name[3:].lower()}"
                    elif name.startswith("is"):
                        comment = f"{indent}// metodo que comprueba si es {name[2:].lower()} y devuelve verdadero o falso"
                    elif name == "main":
                        comment = f"{indent}// metodo principal que arranca la ejecucion de la clase"
                    elif name == "actionPerformed":
                        comment = f"{indent}// metodo que reacciona a los eventos de los botones o interfaz"
                    else:
                        comment = f"{indent}// metodo encargado de la funcion {name.lower()} recibiendo parametros: {args if args.strip() else 'ninguno'}"
                    
                    new_lines.append(comment + "\n")
                
                # Match constructor
                elif not m_match:
                    c_match = constructor_pattern.match(line)
                    if c_match and not is_commented:
                        indent = c_match.group(1)
                        name = c_match.group(3)
                        args = c_match.group(4)
                        # Ensure it's likely a constructor (name matches file)
                        if name in file:
                            comment = f"{indent}// constructor de la clase {name.lower()} que inicializa sus variables con parametros: {args if args.strip() else 'ninguno'}"
                            new_lines.append(comment + "\n")
                        else:
                            # Might be a regular method missing return type (invalid java but possible regex match)
                            pass
                
                # Match field
                if not m_match and not constructor_pattern.match(line):
                    f_match = field_pattern.match(line)
                    if f_match and not is_commented and " return " not in line:
                        indent = f_match.group(1)
                        name = f_match.group(6)
                        comment = f"{indent}// variable que guarda informacion sobre {name.lower()}"
                        new_lines.append(comment + "\n")

                new_lines.append(line)
                i += 1
            
            with open(filepath, "w", encoding="utf-8") as f:
                f.writelines(new_lines)
            print(f"Processed {file}")

