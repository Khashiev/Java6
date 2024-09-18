package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Incorrect number");
        }

        boolean isPrime = true;
        int iter = 1;

        for(int i = 2; i <= Math.floor(Math.sqrt(number)); i++, iter++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }

    public int digitsSum(int number) {
        int sum = 0;

        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }

        return sum;
    }
}
