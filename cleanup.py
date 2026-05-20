import os

base_dir = "/Users/gerardlechosa/Documents/Github/Joc-Pengui-GR04/jocpinguiFinal/src/jocpinguiFinal"

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
                if "variable que guarda informacion sobre" in line:
                    # check next line
                    if i + 1 < len(lines) and "return " in lines[i+1]:
                        # Skip this comment
                        i += 1
                        continue
                new_lines.append(line)
                i += 1
            
            with open(filepath, "w", encoding="utf-8") as f:
                f.writelines(new_lines)
            print(f"Cleaned up {file}")
