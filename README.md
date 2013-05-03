# Splash creator

Example:

```

    <plugin>
        <groupId>org.bitstrings.maven.plugins</groupId>
        <artifactId>splasher-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <configuration>
            <outputFile>image.png</outputFile>

            <canvas>
                <width>640</width>
                <height>480</height>
                <color>#FFFFFF</color>
            </canvas>

            <drawTexts>
                <drawText>
                    <text>I Rulez!</text>
                    <family>sans-serif</family>
                    <style>bold</style>
                    <x>64</x>
                    <y>128</y>
                    <color>#000000</color>
                    <size>32</size>
                    <antialias>true</antialias>
                </drawText>
            </drawTexts>
        </configuration>
    </plugin>

```
