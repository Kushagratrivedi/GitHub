using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FDMTermProject
{
    public class RandomPicker
    {
        private int Min;
        private int Max;
        private int[] Array;
        private int currentIndex = -1;
        private RandomSingleton RandomSingleton;

        /// <summary>
        /// minimum inclusive but maximum not inclusive
        /// </summary>
        /// <param name="value1">value1</param>
        /// <param name="value2">value2</param>
        public RandomPicker(int value1, int value2)
        {
            RandomSingleton = new RandomSingleton();
            this.Min = Math.Min(value1, value2);
            this.Max = Math.Max(value1, value2);
            SetArray();
        }

        public int GetNextPick()
        {
            currentIndex = (currentIndex + 1) % this.Max;
            return Array[currentIndex];
        }

        private void SetArray()
        {
            Random _random = RandomSingleton.GetRandom();
            int[] array = Enumerable.Range(this.Min, this.Max).ToArray();
            int n = array.Length;
            for (int i = 0; i < n; i++)
            {
                int r = i + (int)(_random.NextDouble() * (n - i));
                int t = array[r];
                array[r] = array[i];
                array[i] = t;
            }

            this.Array = array;
        }
    }

    public sealed class RandomSingleton
    {
        static readonly RandomSingleton _instance = new RandomSingleton();

        static Random _random = new Random();

        public static RandomSingleton Instance
        {
            get
            {
                return _instance;
            }
        }

        public Random GetRandom()
        {
            return _random;
        }
    }
    /* Old Code
    public class RandomPicker<T>
    {
        private Dictionary<T, int> m_Items;
        private Random m_Random;
        private T m_LastPick;

        public RandomPicker(List<T> items)
        {
            if (items.Count < 2)
                throw new ArgumentOutOfRangeException("The list must contain at least two items.");

            this.m_Items = new Dictionary<T, int>();
            items.ForEach(x => this.m_Items.Add(x, 0));
            m_Random = new Random();
        }

        private List<T> GetCandidateList()
        {
            List<T> list = m_Items.Keys.Where(x => m_Items[x] == m_Items.Values.Min()).ToList();
            list.Remove(this.m_LastPick);
            if (list.Count < 1)
            {
                list = m_Items.Keys.ToList();
                list.Remove(this.m_LastPick);
            }
            return list;
        }

        public T PickItem()
        {
            List<T> candidateList = GetCandidateList();
            int pickedIndex = m_Random.Next(0, candidateList.Count);
            m_Items[candidateList[pickedIndex]]++;
            m_LastPick = candidateList[pickedIndex];

            return candidateList[pickedIndex];

        }
    }
    */
}
