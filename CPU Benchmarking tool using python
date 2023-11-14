import time
import psutil
import platform
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
from ttkthemes import ThemedTk
from threading import Thread



# Global variable for total calculations
total_calculations = 10**8  # Increase the number of calculations to make it take longer

# Function to perform complex arithmetic calculations for benchmarking
def perform_complex_arithmetic_benchmark():
    calculations_done = 0
    start_time = time.time()

    while calculations_done < total_calculations:
        # Perform complex arithmetic operations (exponentiation and square root)
        result = (2**32 + 1) ** 0.5
        calculations_done += 1

    end_time = time.time()
    elapsed_time = end_time - start_time
    return elapsed_time

# Function to assess CPU performance
def assess_cpu_performance(elapsed_time):
    cpu_percent = psutil.cpu_percent(interval=1)  # Monitor CPU usage for 1 second
    # Calculate calculations per second
    calculations_per_second = int(total_calculations / elapsed_time)

    if calculations_per_second > 500:
        return "Excellent", cpu_percent
    elif calculations_per_second > 100:
        return "Good", cpu_percent
    else:
        return "Poor", cpu_percent

# Function to display system information
def display_system_info():
    ram_info = f"RAM: {psutil.virtual_memory().total / (1024**3):.2f} GB"
    cpu_info = f"Processor: {platform.processor()}"
    system_info = f"System: {platform.system()} {platform.release()}"
    info_text = f"{system_info}\n{cpu_info}\n{ram_info}"
    messagebox.showinfo("System Information", info_text)

# Function to run the benchmark and display the results
def run_benchmark():
    global benchmark_started
    benchmark_started = True

    def background_benchmark():
        elapsed_time = perform_complex_arithmetic_benchmark()
        performance, cpu_percent = assess_cpu_performance(elapsed_time)
        result_text = f"CPU Performance: {performance}\nElapsed Time: {elapsed_time:.2f} seconds\nCPU Usage: {cpu_percent:.2f}%"

        if performance == "Poor":
            recommendations = "Recommended actions:\n1. Close unnecessary background applications.\n2. Upgrade your CPU if possible.\n3. Check for overheating and clean the CPU's cooling system."
            result_text += f"\n\n{recommendations}"

        result_label.config(text=result_text)
        start_button["state"] = "normal"
        # Show the additional buttons and hide the "Show System Info" button
        info_button.pack_forget()
        retake_button.pack()
        system_info_button.pack()
        exit_button.pack()
        # Stop the progress bar and hide it when the computation is completed
        progress_bar.stop()
        progress_bar.pack_forget()

    start_button["state"] = "disabled"
    result_label.config(text="Working...")
    # Show and start the progress bar when the benchmark starts
    progress_bar.pack(fill='x', expand=True)
    progress_bar.start()

    benchmark_thread = Thread(target=background_benchmark)
    benchmark_thread.start()

# Function to reset the benchmark
def retake_benchmark():
    global benchmark_started
    benchmark_started = False
    result_label.config(text="")
    retake_button.pack_forget()
    system_info_button.pack_forget()
    exit_button.pack_forget()
    info_button.pack()

# Create the themed main window
window = ThemedTk(theme="arc")  # You can change the theme as needed
window.title("CPU Benchmarking Tool")


window.configure(background='black')


style = ttk.Style()

style.map("Custom.TButton",
          foreground=[('pressed', 'green'), ('active', 'green')],
          background=[('pressed', 'grey'), ('active', 'black')])
# Set the background color and text color for buttons and labels
style.configure('TButton', background='grey', foreground='green')
style.configure('TLabel', background='black', foreground='green')
style.configure('TFrame', background='grey')
# Create a button to start the benchmark
start_button = ttk.Button(window, text="Start CPU Benchmark", command=run_benchmark)
start_button.pack(pady=10)

# Create a button to display system information
info_button = ttk.Button(window, text="Show System Info", command=display_system_info)
info_button.pack()

# Create a label to display the results
result_label = ttk.Label(window, text="")
result_label.pack(pady=10)  # Add some padding to create space between the result and buttons

# Create a progress bar (hidden by default)
progress_bar = ttk.Progressbar(window, mode="indeterminate")
# Initially, the progress bar is hidden

# Create buttons to be displayed after the benchmark is completed
retake_button = ttk.Button(window, text="Retake CPU Benchmark", command=retake_benchmark)
system_info_button = ttk.Button(window, text="View System Info", command=display_system_info)
exit_button = ttk.Button(window, text="Exit", command=window.quit)

# Global variable to track benchmark start
benchmark_started = False

# Start the GUI main loop
window.mainloop()
